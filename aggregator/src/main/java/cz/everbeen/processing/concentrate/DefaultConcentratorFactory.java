package cz.everbeen.processing.concentrate;

import cz.cuni.mff.d3s.been.taskapi.ResultMapping;
import cz.everbeen.processing.Concentrator;
import cz.everbeen.processing.ConcentratorFactory;
import cz.everbeen.processing.arithmetics.Arithmetics;
import cz.everbeen.processing.arithmetics.ArithmeticsConfigurationException;
import cz.everbeen.processing.arithmetics.ArithmeticsFactory;

import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Default implementation of the {@link cz.everbeen.processing.ConcentratorFactory}.
 *
 * @author darklight
 */
public class DefaultConcentratorFactory implements ConcentratorFactory {

	static final String NORM_REGEX = "norm\\(([a-zA-Z_]*), *([0-9]+)\\)";

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
	public Concentrator forAlias(String expression, ResultMapping resultMapping, String [] groupingColNames) throws ConcentratorSetupException {
		final Matcher normMatcher = Pattern.compile(NORM_REGEX).matcher(expression);
		if (normMatcher.matches()) {
			final String varName = normMatcher.group(1);
			final Integer intervalCount = Integer.valueOf(normMatcher.group(2));
			return new NormIntervalConcentrator(
					instantiateArithmetics(resultMapping, varName),
					varName,
					intervalCount
			);
		}

		// TODO matchers for more arithmetics types
		throw new ConcentratorSetupException(String.format(
				"No aggregator found for expression [%s]",
				expression
		));
	}

	Map<String, Pattern> getPatterns() {
		final Map<String, Pattern> patterns = new TreeMap<String,Pattern>();
		patterns.put(NORM_REGEX, Pattern.compile(NORM_REGEX));
		return patterns;
	}

	private Arithmetics<? extends Number> instantiateArithmetics(ResultMapping resultMapping, String fieldName) throws ConcentratorSetupException {
		final Class<? extends Number> aggrValueClass = (Class<? extends Number>) resultMapping.typeForName(fieldName);
		try {
			return ArithmeticsFactory.newInstance(aggrValueClass);
		} catch (ArithmeticsConfigurationException e) {
			throw new ConcentratorSetupException(String.format(
					"Failed to setup arithmetics unit for type [%s]",
					aggrValueClass == null ? "null" : aggrValueClass.getCanonicalName()
			), e);
		}
	}
}
