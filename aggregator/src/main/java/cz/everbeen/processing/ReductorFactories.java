package cz.everbeen.processing;

import cz.cuni.mff.d3s.been.taskapi.ResultMapping;

import java.util.*;

/**
 * Facade utility class providing easy access to {@link cz.everbeen.processing.ConcentratorFactory} and {@link cz.everbeen.processing.AggregatorFactory} instances.
 *
 * @author darklight
 */
class ReductorFactories {

	private final SortedMap<Integer, AggregatorFactory> aggregatorFactories;
	private final SortedMap<Integer, ConcentratorFactory> concentratorFactories;

	private final String [] groupingColNames;
	private final ResultMapping resultMapping;

	ReductorFactories(String [] groupingColNames, ResultMapping resultMapping) {
		this.aggregatorFactories = new TreeMap<Integer, AggregatorFactory>();
		this.concentratorFactories = new TreeMap<Integer, ConcentratorFactory>();
		this.groupingColNames = groupingColNames;
		this.resultMapping = resultMapping;
	}

	public void init() throws DataProcessingException {
		initConcentrators();
		initAggregators();
	}

	public Aggregator loadAggregator(String expression) throws DataProcessingException {
		final Iterator<AggregatorFactory> factoryIterator = aggregatorFactories.values().iterator();
		while (factoryIterator.hasNext()) {
			final AggregatorFactory factory = factoryIterator.next();
			if (factory.evaluatesAlias(expression)) return factory.forAlias(
					expression,
					resultMapping,
					groupingColNames
			);
		}
		throw new DataProcessingException(String.format("No aggregator factory found for expression [%s]", expression));
	}

	public Concentrator loadConcentrator(String expression) throws DataProcessingException {
		final Iterator<ConcentratorFactory> factoryIterator = concentratorFactories.values().iterator();
		while (factoryIterator.hasNext()) {
			final ConcentratorFactory factory = factoryIterator.next();
			if (factory.evaluatesAlias(expression)) return factory.forAlias(
					expression,
					resultMapping,
					groupingColNames
			);
		}
		throw new DataProcessingException(String.format("No concentrator factory found for expression [%s]", expression));
	}

	private void initAggregators() {
		final ServiceLoader<AggregatorFactory> sl = ServiceLoader.load(AggregatorFactory.class);
		final Iterator<AggregatorFactory> fit = sl.iterator();
		while (fit.hasNext()) {
			final AggregatorFactory f = fit.next();
			// order negative, highest priorities will be stored first
			aggregatorFactories.put(- f.loadPriority(), f);
		}
	}

	private void initConcentrators() {
		final ServiceLoader<ConcentratorFactory> sl = ServiceLoader.load(ConcentratorFactory.class);
		final Iterator<ConcentratorFactory> fit = sl.iterator();
		while (fit.hasNext()) {
			final ConcentratorFactory f = fit.next();
			// order negative, highest priorities will be stored first
			concentratorFactories.put(- f.loadPriority(), f);
		}
	}
}
