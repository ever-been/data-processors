package cz.everbeen.processing.aggregate;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.util.Map;
import java.util.TreeMap;

/**
 * Generic data cube. Doesn't support partial queries.
 *
 * @author darklight
 */
class AggregatedDataCube<T> {

	private final Map<String, T> valueMap;
	private final BASE64Encoder b64;

	AggregatedDataCube() {
		valueMap = new TreeMap<String, T>();
		b64 = new BASE64Encoder();
	}

	/**
	 * Get the value stored for given grouping column values
	 *
	 * @param groupingColumnValues Values of the grouping columns
	 *
	 * @return Value of aggregated variable, grouped by given column values
	 */
	public T getValue(Object [] groupingColumnValues) {
		return valueMap.get(columnsToKey(groupingColumnValues));
	}

	/**
	 * Get the value stored for values contained in passed object
	 * @param object
	 * @param groupingColumnKeys
	 * @return
	 */
	public T getValue(Map<String, Object> object, String [] groupingColumnKeys) {
		return getValue(recordToColumnValues(object, groupingColumnKeys));
	}

	/**
	 * Set the value associated with given grouping column values
	 *
	 * @param groupingColumnValues Values of the grouping columns
	 *
	 * @param value New value to associate with given grouping column values
	 */
	public void setValue(Object [] groupingColumnValues, T value) {
		valueMap.put(columnsToKey(groupingColumnValues), value);
	}

	/**
	 * Set value associated with grouping column values presented in this record
	 *
	 * @param object Presented record
	 * @param groupingColumnKeys Keys (field names) of grouping columns
	 * @param value Value to set
	 */
	public void setValue(Map<String, Object> object, String [] groupingColumnKeys, T value) {
		setValue(recordToColumnValues(object, groupingColumnKeys), value);
	}

	/**
	 * Clear values bound in this cube.
	 */
	public void clear() {
		valueMap.clear();
	}

	private Object[] recordToColumnValues(Map<String, Object> record, String [] groupingColumnKeys) {
		final Object [] groupingColumnValues = new Object [groupingColumnKeys.length];
		for (int i = 0; i < groupingColumnKeys.length; ++i) groupingColumnValues[i] = record.get(groupingColumnKeys[i]);
		return groupingColumnValues;
	}

	private String columnsToKey(Object [] groupingColumnValues) {
		final StringBuilder sb = new StringBuilder();
		for (Object colVal: groupingColumnValues) {
			if (sb.length() > 0) sb.append(",");
			sb.append(b64.encode(String.valueOf(colVal).getBytes()));
		}
		return sb.toString();
	}
}
