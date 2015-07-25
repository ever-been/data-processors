package cz.everbeen.processing.aggregate;

import cz.everbeen.processing.Aggregator;
import cz.everbeen.processing.arithmetics.Arithmetics;

import java.util.Collection;
import java.util.Map;

/**
 * Aggregator that sums values
 *
 * @author darklight
 */
class SumAggregator<T extends Number> implements Aggregator<T> {

	private final Arithmetics<T> au;

	private final String aggregatedFieldName;
	private final String canonicalAggrName;
	private final String [] groupingColNames;

	private AggregatedDataCube<T> sumCube;

	/**
	 * Create sum aggregator,
	 * @param groupingColNames
	 */
	public SumAggregator(
			Arithmetics<T> au,
			String aggregatedFieldName,
			String [] groupingColNames
	) {
		this.au = au;
		this.aggregatedFieldName = aggregatedFieldName;
		this.canonicalAggrName = String.format("sum(%s)", aggregatedFieldName);
		this.groupingColNames = groupingColNames;

		this.sumCube = new AggregatedDataCube<T>();
	}

	@Override
	public void add(Map<String, Object> result, Map<String, Object> reducedResult) {
		final T value = (T) result.get(aggregatedFieldName);
		T sum = sumCube.getValue(reducedResult, groupingColNames);
		sum = (sum == null) ? value : au.add(sum, value);
		sumCube.setValue(reducedResult, groupingColNames, sum);
	}

	@Override
	public void writeAggregatedValue(Map<String, Object> reducedResult) {
		reducedResult.put(canonicalAggrName, sumCube.getValue(reducedResult, groupingColNames));
	}

	@Override
	public void initialize(Collection<Map<String, Object>> resultSet) {
		// nothing to do
	}

	@Override
	public void reset() {
		sumCube.clear();
	}
}
