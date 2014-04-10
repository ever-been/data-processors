package cz.everbeen.processing;

import cz.cuni.mff.d3s.been.core.persistence.EntityID;
import cz.cuni.mff.d3s.been.evaluators.EvaluatorResult;
import cz.cuni.mff.d3s.been.mq.MessagingException;
import cz.cuni.mff.d3s.been.persistence.DAOException;
import cz.cuni.mff.d3s.been.persistence.QueryBuilder;
import cz.cuni.mff.d3s.been.taskapi.Evaluator;
import cz.cuni.mff.d3s.been.taskapi.TaskException;
import cz.everbeen.processing.configuration.ProcessingConfiguration;
import cz.everbeen.processing.configuration.ProcessingConfigurationParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

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
		// TODO pConf must hold the type map at this point
		final Map<String, Class<?>> typeMap = new TreeMap<String, Class<?>>();
		pConfParser.parseProcessingConfiguration(pConf);

		final QueryBuilder qb = new QueryBuilder().on(new EntityID().withKind("result").withGroup(pConf.getGroupId()));
		if (pConf.getFrom() != null) {
			qb.with("created").above(pConf.getFrom());
		}
		if (pConf.getTo() != null) {
			qb.with("created").below(pConf.getTo());
		}

		return logic.process(results.query(qb.fetch(), typeMap), pConf);
	}
}