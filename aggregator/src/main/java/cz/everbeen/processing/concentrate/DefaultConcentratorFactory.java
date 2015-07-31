package cz.everbeen.processing.concentrate;

import cz.cuni.mff.d3s.been.results.PrimitiveType;
import cz.cuni.mff.d3s.been.results.PrimitiveTypeException;
import cz.cuni.mff.d3s.been.results.ResultMapping;
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

	static final String NORM_REGEX = "norm\\(([a-zA-Z_]+), *([0-9]+)\\)";
	static final String ID_REGEX = "([a-zA-Z_]+)";

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
			assertVariablesExist(resultMapping, varName);
			final Integer intervalCount = Integer.valueOf(normMatcher.group(2));
			return new NormIntervalConcentrator(
					instantiateArithmetics(resultMapping, varName),
					varName,
					intervalCount
			);
		}

		final Matcher idMatcher = Pattern.compile(ID_REGEX).matcher(expression);
		if (idMatcher.matches()) {
			final String varName = idMatcher.group(1);
			assertVariablesExist(resultMapping, varName);
			return new IdConcentrator(varName);
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
		patterns.put(ID_REGEX, Pattern.compile(ID_REGEX));
		return patterns;
	}

	private Arithmetics<? extends Number> instantiateArithmetics(ResultMapping resultMapping, String fieldName) throws ConcentratorSetupException {
		final PrimitiveType arithmeticsType;
		try {
			arithmeticsType = PrimitiveType.fromTypeAlias(resultMapping.typeForName(fieldName));
		} catch (PrimitiveTypeException e) {
			throw new ConcentratorSetupException("Invalid type mapping", e);
		}
		if (!arithmeticsType.isNumeric())
			throw new ConcentratorSetupException(String.format(
					"Type [%s] is not numeric",
					arithmeticsType.getTypeAlias()
			));
		try {
			return ArithmeticsFactory.newInstance(
					(Class<? extends Number>) arithmeticsType.getPrimitiveClass()
			);
		} catch (ArithmeticsConfigurationException e) {
			throw new ConcentratorSetupException(String.format(
					"Failed to setup arithmetics unit for type [%s]",
					arithmeticsType.getPrimitiveClass().getCanonicalName()
			), e);
		}
	}

	/**
	 * Utility method that checks whether all variables used in matched expression are present in the type mapping.
	 *
	 * @param resultMapping Mapping
	 * @param variables Variables to validate
	 *
	 * @throws ConcentratorSetupException When one of the variables is found not to exist
	 */
	private void assertVariablesExist(ResultMapping resultMapping, String... variables) throws ConcentratorSetupException {
		for (String var: variables) {
			if (resultMapping.typeForName(var) == null) throw new ConcentratorSetupException(String.format(
					"Variable [%s] not declared",
					var
			));
		}
	}
}
