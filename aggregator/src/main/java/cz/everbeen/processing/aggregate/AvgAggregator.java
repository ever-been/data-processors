package cz.everbeen.processing.aggregate;

import cz.cuni.mff.d3s.been.results.PrimitiveType;
import cz.everbeen.processing.Aggregator;
import cz.everbeen.processing.arithmetics.Arithmetics;

import java.util.Collection;
import java.util.Map;

/**
 * Averaging aggregator implementation
 *
 * @author darklight
 */
public class AvgAggregator<T extends Number> implements Aggregator<T> {

	private final Arithmetics<T> au;

	private final String aggregatedFieldName;
	private final String canonicalAggrName;
	private final PrimitiveType aggregationPType;
	private final String [] groupingColNames;

	private AggregatedDataCube<T> sumCube;
	private AggregatedDataCube<Integer> countCube;

	/**
	 * Create avg aggregator,
	 * @param groupingColNames
	 */
	public AvgAggregator(
			Arithmetics<T> au,
			String aggregatedFieldName,
			PrimitiveType aggregationPType,
			String [] groupingColNames
	) {
		this.au = au;
		this.aggregatedFieldName = aggregatedFieldName;
		this.canonicalAggrName = String.format("avg(%s)", aggregatedFieldName);
		this.aggregationPType = aggregationPType;
		this.groupingColNames = groupingColNames;

		this.sumCube = new AggregatedDataCube<T>();
		this.countCube = new AggregatedDataCube<Integer>();
	}

	@Override
	public String getColName() {
		return canonicalAggrName;
	}

	@Override
	public PrimitiveType getColType() {
		return aggregationPType;
	}

	@Override
	public void add(Map<String, Object> result, Map<String, Object> reducedResult) {
		final T value = (T) result.get(aggregatedFieldName);
		T sum = sumCube.getValue(reducedResult, groupingColNames);
		sum = (sum == null) ? value : au.add(sum, value);
		sumCube.setValue(reducedResult, groupingColNames, sum);

		Integer count = countCube.getValue(reducedResult, groupingColNames);
		count = (count == null) ? 1 : count + 1;
		countCube.setValue(reducedResult, groupingColNames, count);
	}

	@Override
	public void writeAggregatedValue(Map<String, Object> reducedResult) {
		final T sum = sumCube.getValue(reducedResult, groupingColNames);
		if (sum == null) return; // not interested in calculating null / null
		reducedResult.put(canonicalAggrName, au.div(
				sum,
				countCube.getValue(reducedResult, groupingColNames)
		));
	}

	@Override
	public void initialize(Collection<Map<String, Object>> resultSet) {
		// nothing to do
	}

	@Override
	public void reset() {
		sumCube.clear();
		countCube.clear();
	}
}
