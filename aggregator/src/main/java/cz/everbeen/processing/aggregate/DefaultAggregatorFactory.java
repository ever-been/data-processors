package cz.everbeen.processing.aggregate;

import cz.cuni.mff.d3s.been.results.PrimitiveType;
import cz.cuni.mff.d3s.been.results.PrimitiveTypeException;
import cz.cuni.mff.d3s.been.results.ResultMapping;
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
	static final String AVG_REGEX = "avg\\(([a-zA-z_]+)\\)";
	static final String COUNT_REGEX = "count\\( *\\)";
	static final String MIN_REGEX= "min\\(([a-zA-z_]+)\\)";
	static final String MAX_REGEX = "max\\(([a-zA-z_]+)\\)";

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
			final String varName = sumMatcher.group(1);
			final PrimitiveType argType = getTypeForField(resultMapping, varName);
			assertArithmetic(argType, varName, "sum");
			return new SumAggregator(
					instantiateArithmetics(resultMapping, varName),
					varName,
					argType,
					groupingColNames
			);
		}

		final Matcher avgMatcher = Pattern.compile(AVG_REGEX).matcher(expression);
		if (avgMatcher.matches()) {
			final String varName = avgMatcher.group(1);
			final PrimitiveType varType = getTypeForField(resultMapping, varName);
			assertArithmetic(varType, varName, "avg");
			return new AvgAggregator(
					instantiateArithmetics(resultMapping, varName),
					varName,
					varType,
					groupingColNames
			);
		}

		final Matcher countMatcher = Pattern.compile(COUNT_REGEX).matcher(expression);
		if (countMatcher.matches()) {
			return new CountAggregator(
					groupingColNames
			);
		}

		// TODO matchers for more arithmetics types
		throw new AggregatorSetupException(String.format(
				"No aggregator found for expression [%s]",
				expression
		));
	}

	PrimitiveType getTypeForField(ResultMapping resultMapping, String fieldName) throws AggregatorSetupException{
		assertVariablesExist(resultMapping, fieldName);
		final PrimitiveType argType;
		try {
			argType = PrimitiveType.fromTypeAlias(resultMapping.typeForName(fieldName));
		} catch (PrimitiveTypeException e) {
			throw new AggregatorSetupException("Invalid type mapping", e);
		}
		return argType;
	}

	private void assertArithmetic(PrimitiveType type, String varName, String funcName) throws AggregatorSetupException {
		if (!type.isNumeric()) throw new AggregatorSetupException(String.format(
				"Invalid invocation of function [%s]: variable [%s] is of type [%s] is non-numeric",
				funcName,
				varName,
				type.getTypeAlias()
		));
	}

	Map<String, Pattern> getPatterns() {
		final Map<String, Pattern> patterns = new TreeMap<String,Pattern>();
		patterns.put(SUM_REGEX, Pattern.compile(SUM_REGEX));
		patterns.put(AVG_REGEX, Pattern.compile(AVG_REGEX));
		patterns.put(COUNT_REGEX, Pattern.compile(COUNT_REGEX));
		return patterns;
	}

	private Arithmetics instantiateArithmetics(ResultMapping resultMapping, String fieldName) throws AggregatorSetupException {
		final PrimitiveType arithmeticsType;
		try {
			arithmeticsType = PrimitiveType.fromTypeAlias(resultMapping.typeForName(fieldName));
		} catch (PrimitiveTypeException e) {
			throw new AggregatorSetupException("Invalid type mapping", e);
		}
		if (!arithmeticsType.isNumeric())
			throw new AggregatorSetupException(String.format(
					"Type [%s] is not numeric",
					arithmeticsType.getTypeAlias()
			));

		try {
			return ArithmeticsFactory.newInstance((Class<? extends Number>)arithmeticsType.getPrimitiveClass());
		} catch (ArithmeticsConfigurationException e) {
			throw new AggregatorSetupException(String.format(
					"Failed to setup arithmetics unit for type [%s]",
					arithmeticsType.getTypeAlias()
			), e);
		}
	}

	/**
	 * Utility method that checks whether all variables used in matched expression are present in the type mapping.
	 *
	 * @param resultMapping Mapping
	 * @param variables Variables to validate
	 *
	 * @throws AggregatorSetupException When one of the variables is found not to exist
	 */
	private void assertVariablesExist(ResultMapping resultMapping, String... variables) throws AggregatorSetupException {
		for (String var: variables) {
			if (resultMapping.typeForName(var) == null) throw new AggregatorSetupException(String.format(
					"Variable [%s] not declared",
					var
			));
		}
	}
}
