package cz.everbeen.export.xml;

import cz.cuni.mff.d3s.been.evaluators.EvaluatorResult;
import cz.cuni.mff.d3s.been.taskapi.ResultMapping;
import cz.everbeen.processing.DataProcessingException;
import cz.everbeen.processing.DataProcessorLogic;
import cz.everbeen.processing.configuration.ProcessingConfiguration;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * Task that exports selected data to XML.
 *
 * @author darklight
 */
public class XMLExportLogic implements DataProcessorLogic {

	private static final SimpleDateFormat FILENAME_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS");

	private static final String ROOT_TAG = "data";
	private static final String RESULT_TAG = "entry";

	private boolean elementMode = false;

	@Override
	public String getName() {
		return "export-xml";
	}

	@Override
	public ProcessingConfiguration createConfig() {
		return new XMLExportConfiguration();
	}

	@Override
	public EvaluatorResult process(Collection<Map<String, Object>> results, ResultMapping mapping, ProcessingConfiguration conf) throws DataProcessingException {
		final ByteArrayOutputStream bao = new ByteArrayOutputStream();
		final XMLStreamWriter xsw;
		try {
			xsw = XMLOutputFactory.newInstance().createXMLStreamWriter(new BufferedOutputStream(bao), "UTF-8");
		} catch (XMLStreamException e) {
			throw new DataProcessingException("Could not initialize XML serializer", e);
		}

		try {
			doWriteResults(xsw, results);
		} catch (XMLStreamException e) {
			throw new DataProcessingException("Failed to serialize result collection to XML" ,e);
		}

		try {
			xsw.close();
		} catch (XMLStreamException e) {
			throw new DataProcessingException("Could not close result stream", e);
		}

		final Date creationDate = new Date();
		final EvaluatorResult evaluatorResult = new EvaluatorResult();
		evaluatorResult.setData(bao.toByteArray());
		evaluatorResult.setMimeType(EvaluatorResult.MIME_TYPE_PLAIN);
		evaluatorResult.setTimestamp(creationDate.getTime());
		evaluatorResult.setFilename("export-xml_" + FILENAME_DATE_FORMAT.format(creationDate) + ".xml");
		return evaluatorResult;
	}

	private void doWriteResults(XMLStreamWriter xsw, Collection<Map<String, Object>> results) throws XMLStreamException {
		xsw.writeStartDocument();
		xsw.writeStartElement(ROOT_TAG);

		for(Map<String, Object> result: results) {
			if (elementMode)
				doWriteResultInElements(xsw, result);
			else
				doWriteResultInAttributes(xsw, result);
		}

		xsw.writeEndElement();
		xsw.writeEndDocument();
	}

	private void doWriteResultInAttributes(XMLStreamWriter xsw, Map<String, Object> result) throws XMLStreamException {
		xsw.writeEmptyElement(RESULT_TAG);
		for(Map.Entry<String, Object> field: result.entrySet()) {
			xsw.writeAttribute(field.getKey(), field.getValue().toString());
		}
	}

	private void doWriteResultInElements(XMLStreamWriter xsw, Map<String, Object> result) throws XMLStreamException {
		xsw.writeStartElement(RESULT_TAG);
		for(Map.Entry<String, Object> field: result.entrySet()) {
			xsw.writeStartElement(field.getKey());
			xsw.writeCData(field.getValue().toString());
			xsw.writeEndElement();
		}
		xsw.writeEndElement();
	}
}
