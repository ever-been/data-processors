package cz.everbeen.export.json;

import cz.cuni.mff.d3s.been.evaluators.EvaluatorResult;
import cz.cuni.mff.d3s.been.taskapi.ResultMapping;
import cz.everbeen.processing.DataProcessingException;
import cz.everbeen.processing.DataProcessorLogic;
import cz.everbeen.processing.configuration.ProcessingConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;

/**
 * Task that exports selected data to JSON.
 */
public class JSONExportLogic implements DataProcessorLogic {

	private static final Logger log = LoggerFactory.getLogger(JSONExportLogic.class);

	@Override
	public ProcessingConfiguration createConfig() {
		return new ProcessingConfiguration();
	}

	@Override
	public EvaluatorResult process(Collection<Map<String, Object>> results, ResultMapping mapping, ProcessingConfiguration conf) throws DataProcessingException {
		log.info("Json export configured with {}", conf.toString());
		log.info("Fetched {} results", results.size());
		return null;
	}
}
