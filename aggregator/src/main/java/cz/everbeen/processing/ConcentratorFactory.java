package cz.everbeen.processing;

import cz.cuni.mff.d3s.been.results.ResultMapping;
import cz.everbeen.processing.concentrate.ConcentratorSetupException;

/**
 * Factory for {@link cz.everbeen.processing.Concentrator} objects.
 *
 * @author darklight
 */
public interface ConcentratorFactory {

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
	 * Create concentrator instance for given expression.
	 *
	 * @param expression Expression to evaluate, customarily something like "norm(t, 10)". Taken from task properties.
	 * @param resultMapping Dataset RTTI
	 * @param groupingColNames Names of the grouping columns (fields)
	 *
	 * @return Concentrator instance evaluating given expression
	 *
	 * @throws ConcentratorSetupException When no concentrator can be instantiated for given expression
	 */
	Concentrator forAlias(
			String expression,
			ResultMapping resultMapping,
			String [] groupingColNames
	) throws ConcentratorSetupException;
}
