package cz.everbeen.processing;

import cz.cuni.mff.d3s.been.results.PrimitiveType;

import java.util.Collection;
import java.util.Map;

/**
 * Generic value aggregator
 *
 * @author darklight
 */
public interface Aggregator<VType> {

	/**
	 * Add a single result value to the aggregator.
	 *
	 * @param result Result to add
	 * @param reducedResult Reduced result form, containing concentrated values ONLY
	 */
	void add(Map<String, Object> result, Map<String, Object> reducedResult);

	/**
	 * Write the aggregated value for specified entry (based on passed reduced result), previously assembled via {#add()}, into passed result.
	 * !!! WARNING !!!:
	 * Modifies passed object.
	 *
	 * @param reducedResult Reduced values of grouping columns
	 */
	void writeAggregatedValue(Map<String, Object> reducedResult);

	/**
	 * Initialize the aggregator's configuration to suit the processing of given collection.
	 * @param resultSet Result collection that will be processed next
	 */
	void initialize(Collection<Map<String, Object>> resultSet);

	/**
	 * Get the name of the dataset column this aggregator generates
	 *
	 * @return Column name
	 */
	String getColName();

	/**
	 * Get the type of values this aggregator generates
	 *
	 * @return Column type
	 */
	PrimitiveType getColType();

	/**
	 * Reset cached data, reinitializing precalculated aggregation values.
	 * Puts the aggregator back to the state in which it would be freshly after instantiation.
	 */
	void reset();
}
