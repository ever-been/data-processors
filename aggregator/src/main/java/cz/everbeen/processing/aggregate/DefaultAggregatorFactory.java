package cz.everbeen.processing.aggregate;

import cz.cuni.mff.d3s.been.taskapi.ResultMapping;
import cz.everbeen.processing.Aggregator;
import cz.everbeen.processing.AggregatorFactory;
import cz.everbeen.processing.arithmetics.Arithmetics;
import cz.everbeen.processing.arithmetics.ArithmeticsConfigurationException;
import cz.everbeen.processing.arithmetics.ArithmeticsFactory;

import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Default implementation of the {@link cz.everbeen.processing.AggregatorFactory}
 *
 * @author darklight
 */
public class DefaultAggregatorFactory implements AggregatorFactory {

	static final String SUM_REGEX = "sum\\(([a-zA-z_]+)\\)";

	@Override
	public int loadPriority() {
		return 50;
	}

	@Override
	public boolean evaluatesAlias(String expression) {
		for (Pattern p: getPatterns().values()) if (p.matcher(expression).matches()) return true;
		return false;
	}

	@Override
	public Aggregator forAlias(
			String expression,
			ResultMapping resultMapping,
			String [] groupingColNames
	) throws AggregatorSetupException {

		final Matcher sumMatcher = Pattern.compile(SUM_REGEX).matcher(expression);
		if (sumMatcher.matches()) {
			return new SumAggregator(
					instantiateArithmetics(resultMapping, sumMatcher.group(1)),
					sumMatcher.group(1),
					groupingColNames
			);
		}

		// TODO matchers for more arithmetics types
		throw new AggregatorSetupException(String.format(
				"No aggregator found for expression [%s]",
				expression
		));
	}

	Map<String, Pattern> getPatterns() {
		final Map<String, Pattern> patterns = new TreeMap<String,Pattern>();
		patterns.put(SUM_REGEX, Pattern.compile(SUM_REGEX));
		return patterns;
	}

	private Arithmetics instantiateArithmetics(ResultMapping resultMapping, String fieldName) throws AggregatorSetupException {
		final Class<? extends Number> aggrValueClass = (Class<? extends Number>) resultMapping.typeForName(fieldName);
		try {
			return ArithmeticsFactory.newInstance(aggrValueClass);
		} catch (ArithmeticsConfigurationException e) {
			throw new AggregatorSetupException(String.format(
					"Failed to setup arithmetics unit for type [%s]",
					aggrValueClass.getCanonicalName()
			), e);
		}
	}
}
