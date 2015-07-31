package cz.everbeen.processing;

import java.util.Collection;
import java.util.Map;

/**
 *
 * @author darklight
 *
 * Typed result set field concentrator.
 * This protocol describes the required functionality for a class to function as a field domain reduction mechanism.
 * In order to simplify the ever-present complexity induced by type inference, concentrated domains always end up as {@link java.lang.String} value sets.
 * In spite of that the concentrator's initialization may be more complex, the value reduction functions as a simple A -&gt; B projection, where A is the value's original domain and B is the reduced domain (a limited set of {@link java.lang.String}s.
 * The B domain and mapping are entirely up to the implementor of the concentrator, the aggregation system expects
 *
 * @param <T> runtime type corresponding to domain A
 */
public interface Concentrator<T> {

	/**
	 * Reduce a source object record. Save reduced value in target record.
	 * Maps the source record into concentrated value, then stores this value into target record.
	 *
	 * @param sourceRecord Record to read values from
	 * @param targetRecord Record to store reduced values into
	 */
	void reduce(Map<String, Object> sourceRecord, Map<String, Object> targetRecord);

	/**
	 * Initialize the concentrator for future processing of given result set.
	 *
	 * @param resultSet Result set that will be processed next by this concentrator
	 */
	void initialize(Collection<Map<String, Object>> resultSet);

	/**
	 * Get the name of dataset column this concentrator generates
	 *
	 * @return Column name
	 */
	String getColName();

	/**
	 * Reset the configuration to initial state, discarding all setup and bringing it to the same state in which it would be freshly after instantiation.
	 */
	void reset();
}
