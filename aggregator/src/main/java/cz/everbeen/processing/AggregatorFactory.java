package cz.everbeen.processing;

import cz.cuni.mff.d3s.been.results.ResultMapping;
import cz.everbeen.processing.aggregate.AggregatorSetupException;

/**
 * Factory for {@link cz.everbeen.processing.Aggregator} objects.
 *
 * @author darklight
 */
public interface AggregatorFactory {

	/**
	 * Determine the loading priority of this factory.
	 * Customary values between 1 and 100. Default = 50.
	 * The higher the score, the sooner loads will be attempted from this factory implementation rather than another.
	 *
	 * @return This factory's priority
	 */
	int loadPriority();

	/**
	 * Check whether this factory can evaluate given alias expression or no.
	 *
	 * @return <code>true</code> if this factory can evaluate given alias; <code>false </code> if not
	 */
	boolean evaluatesAlias(String expression);

	/**
	 * Create aggregator for given expression.
	 *
	 * @param expression Expression to evaluate, customarily something like "sum(t)". Taken from task properties.
	 * @param resultMapping Dataset RTTI
	 * @param groupingColNames Names of the grouping columns (fields)
	 *
	 * @return Aggregator instance evaluating given expression
	 */
	Aggregator forAlias(
			String expression,
			ResultMapping resultMapping,
			String [] groupingColNames
	) throws AggregatorSetupException;
}
