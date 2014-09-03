package cz.everbeen.export.xml;

import cz.cuni.mff.d3s.been.evaluators.EvaluatorResult;
import cz.cuni.mff.d3s.been.mq.MessagingException;
import cz.cuni.mff.d3s.been.persistence.DAOException;
import cz.cuni.mff.d3s.been.taskapi.ResultMapping;
import cz.cuni.mff.d3s.been.taskapi.Task;
import cz.cuni.mff.d3s.been.taskapi.TaskException;
import cz.everbeen.processing.DataProcessingException;
import cz.everbeen.processing.DataProcessorLogic;
import cz.everbeen.processing.configuration.ProcessingConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;


public class XMLExportLogic implements DataProcessorLogic {

    private static final Logger log = LoggerFactory.getLogger(XMLExportLogic.class);

	@Override
	public ProcessingConfiguration createConfig() {
		return new ProcessingConfiguration();
	}

	@Override
	public EvaluatorResult process(Collection<Map<String, Object>> results, ResultMapping mapping, ProcessingConfiguration conf) throws DataProcessingException {
		log.info("XML Export configured with {}", conf.toString());
		return null;
	}
}
