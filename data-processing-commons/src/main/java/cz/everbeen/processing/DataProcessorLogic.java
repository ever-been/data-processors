package cz.everbeen.processing;

import cz.cuni.mff.d3s.been.evaluators.EvaluatorResult;
import cz.cuni.mff.d3s.been.taskapi.ResultMapping;
import cz.everbeen.processing.configuration.ProcessingConfiguration;

import java.util.Collection;
import java.util.Map;

/**
 * A generic interface for processing execution logic. An implementation is supposed to get injected to the generic {@link DataProcessor} by the implementing export package.
 *
 * @author darklight
 */
public interface DataProcessorLogic {

	/**
	 * Create the configuration object fo this processing logic
	 *
	 * @return The configuration object
	 */
	ProcessingConfiguration createConfig();

	/**
	 * Do the processing logic
	 *
	 * @param results Results to process
	 * @param mapping Mapping and aliases of the resulting collection
	 * @param conf Parsed configuration
	 *
	 * @throws DataProcessingException
	 */
	EvaluatorResult process(Collection<Map<String, Object>> results, ResultMapping mapping, ProcessingConfiguration conf) throws DataProcessingException;

}
