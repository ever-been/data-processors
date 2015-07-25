package cz.everbeen.export.csv;

import cz.cuni.mff.d3s.been.evaluators.EvaluatorResult;
import cz.cuni.mff.d3s.been.taskapi.ResultMapping;
import cz.everbeen.processing.DataProcessingException;
import cz.everbeen.processing.DataProcessorLogic;
import cz.everbeen.processing.configuration.ProcessingConfiguration;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Task that exports selected data to JSON.
 *
 * @author darklight
 */
public class CSVExportLogic implements DataProcessorLogic {

	private static final SimpleDateFormat FILENAME_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS");

	@Override
	public String getName() {
		return "expot-csv";
	}

	@Override
	public ProcessingConfiguration createConfig() {
		return new ProcessingConfiguration();
	}

	@Override
	public EvaluatorResult process(Collection<Map<String, Object>> results, ResultMapping mapping, ProcessingConfiguration conf) throws DataProcessingException {

		final List<String> fieldNameColl = new ArrayList<String>();
		fieldNameColl.addAll(mapping.getAliases().keySet());
		fieldNameColl.addAll(mapping.getNonAliasedKeys());

		final int colCount = fieldNameColl.size();
		final String [] fieldNames = new String [colCount];

		final ByteArrayOutputStream bao = new ByteArrayOutputStream();
		final Writer w = new OutputStreamWriter(bao);
		final CSVPrinter cprint;
		try {
			cprint = CSVFormat.DEFAULT.withHeader(
				fieldNameColl.toArray(fieldNames)
			).print(w);
		} catch (IOException e) {
			throw new DataProcessingException("Failed to allocate output data buffer", e);
		}

		int recRow = 0;
		for (Map<String, Object> result: results) {
			final Object [] values = new Object[colCount];
			int colIndex = 0;
			for(String colName: fieldNames) {
				values[colIndex++] = result.get(colName);
			}
			++recRow;
			try {
				cprint.printRecord(values);
			} catch (IOException e) {
				throw new DataProcessingException(String.format(
					"Failed to write record no. %d",
					recRow
				), e);
			}
		}

		try {
			w.close();
		} catch (IOException e) {
			throw new DataProcessingException(
				"Failed to flush output data buffer",
				e
			);
		}

		final Date creationDate = new Date();
		final EvaluatorResult evaluatorResult = new EvaluatorResult();
		evaluatorResult.setData(bao.toByteArray());
		evaluatorResult.setMimeType(EvaluatorResult.MIME_TYPE_CSV);
		evaluatorResult.setTimestamp(creationDate.getTime());
		evaluatorResult.setFilename("export-csv_" + FILENAME_DATE_FORMAT.format(creationDate) + ".csv");
		return evaluatorResult;
	}
}
