package cz.everbeen.processing.aggregate;

import cz.cuni.mff.d3s.been.results.PrimitiveType;
import cz.everbeen.processing.Aggregator;

import java.util.Collection;
import java.util.Map;

/**
 * Counting aggregation processor
 *
 * @author darklight
 */
public class CountAggregator<T> implements Aggregator<T> {

	private final String canonicalAggrName;
	private final String [] groupingColNames;

	private AggregatedDataCube<Integer> countCube;

	/**
	 * Create counting aggregator
	 * @param groupingColNames Grouping column names
	 */
	public CountAggregator(
			String [] groupingColNames
	) {
		this.canonicalAggrName = "count";
		this.groupingColNames = groupingColNames;

		this.countCube = new AggregatedDataCube<Integer>();
	}

	@Override
	public String getColName() {
		return canonicalAggrName;
	}

	@Override
	public PrimitiveType getColType() {
		return PrimitiveType.INT;
	}

	@Override
	public void add(Map<String, Object> result, Map<String, Object> reducedResult) {
		Integer count = countCube.getValue(reducedResult, groupingColNames);
		count = (count == null) ? 1 : count + 1;
		countCube.setValue(reducedResult, groupingColNames, count);
	}

	@Override
	public void writeAggregatedValue(Map<String, Object> reducedResult) {
		final Integer count = countCube.getValue(reducedResult, groupingColNames);
		reducedResult.put(canonicalAggrName, count == null ? 0 : count);
	}

	@Override
	public void initialize(Collection<Map<String, Object>> resultSet) {
		// nothing to do
	}

	@Override
	public void reset() {
		countCube.clear();
	}
}
