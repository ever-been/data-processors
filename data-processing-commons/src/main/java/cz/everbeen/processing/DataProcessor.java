package cz.everbeen.processing;

import cz.cuni.mff.d3s.been.evaluators.EvaluatorResult;
import cz.cuni.mff.d3s.been.mq.MessagingException;
import cz.cuni.mff.d3s.been.persistence.DAOException;
import cz.cuni.mff.d3s.been.taskapi.DataSet;
import cz.cuni.mff.d3s.been.taskapi.Evaluator;
import cz.cuni.mff.d3s.been.taskapi.TaskException;
import cz.everbeen.processing.configuration.ProcessingConfiguration;
import cz.everbeen.processing.configuration.ProcessingConfigurationParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * A generic data processor task. Implements the base functionality of data extraction.
 *
 * @author darklight
 */
public class DataProcessor extends Evaluator {

	private static final Logger log = LoggerFactory.getLogger(DataProcessor.class);

	@Override
	protected EvaluatorResult evaluate() throws TaskException, MessagingException, DAOException {

		final Iterator<DataProcessorLogic> iLogic = ServiceLoader.load(DataProcessorLogic.class).iterator();

		if (!iLogic.hasNext()) {
			log.error("No logic found");
		}
		final DataProcessorLogic logic = iLogic.next();
		while (iLogic.hasNext()) {
			log.warn("Excess logic {} truncated", iLogic.next().getClass().getSimpleName());
		}

		final ProcessingConfiguration pConf = logic.createConfig();
		final ProcessingConfigurationParser pConfParser = new ProcessingConfigurationParser(this);
		pConfParser.parseProcessingConfiguration(pConf);
		log.info("Data processor configured with {}", pConf.toString());

		final DataSet dataSet;
		if (pConf.isLoadingDataset()) {
			dataSet = new DatasetLoader(results).loadDataset(pConf);
		} else {
			dataSet = new QueryLoader(results).loadDataset(pConf);
		}

		log.info("Received {} entries for processing", dataSet.getData().size());
		final EvaluatorResult result = logic.process(
				dataSet.getData(),
				dataSet.getResultMapping(),
				pConf
		);
		log.info("Logic '{}' processed entries into result '{}'", logic.getName(), result.getId());
		return result;
	}
}
