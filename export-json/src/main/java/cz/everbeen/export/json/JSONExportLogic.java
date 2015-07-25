package cz.everbeen.export.json;

import cz.cuni.mff.d3s.been.evaluators.EvaluatorResult;
import cz.cuni.mff.d3s.been.taskapi.ResultMapping;
import cz.everbeen.processing.DataProcessingException;
import cz.everbeen.processing.DataProcessorLogic;
import cz.everbeen.processing.configuration.ProcessingConfiguration;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * Task that exports selected data to JSON.
 *
 * @author darklight
 */
public class JSONExportLogic implements DataProcessorLogic {

	private static final SimpleDateFormat FILENAME_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS");

	@Override
	public String getName() {
		return "expot-json";
	}

	@Override
	public ProcessingConfiguration createConfig() {
		return new ProcessingConfiguration();
	}

	@Override
	public EvaluatorResult process(Collection<Map<String, Object>> results, ResultMapping mapping, ProcessingConfiguration conf) throws DataProcessingException {
		final ByteArrayOutputStream bao = new ByteArrayOutputStream();
		final JsonGenerator jgen;
		try {
			jgen = new JsonFactory().createJsonGenerator(new BufferedOutputStream(bao), JsonEncoding.UTF8);
		} catch (IOException e) {
			throw new DataProcessingException("Could not set up JSON generator", e);
		}
		final ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(jgen, results);
		} catch (IOException e) {
			throw new DataProcessingException("Failed to serialize result object", e);
		}
		try {
			jgen.close();
		} catch (IOException e) {
			throw new DataProcessingException("Could not close result stream", e);
		}
		final Date creationDate = new Date();
		final EvaluatorResult evaluatorResult = new EvaluatorResult();
		evaluatorResult.setData(bao.toByteArray());
		evaluatorResult.setMimeType(EvaluatorResult.MIME_TYPE_JSON);
		evaluatorResult.setTimestamp(creationDate.getTime());
		evaluatorResult.setFilename("export-json_" + FILENAME_DATE_FORMAT.format(creationDate) + ".json");
		return evaluatorResult;
	}
}
