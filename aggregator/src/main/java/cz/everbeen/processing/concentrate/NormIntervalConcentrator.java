package cz.everbeen.processing.concentrate;

import cz.everbeen.processing.Concentrator;
import cz.everbeen.processing.arithmetics.Arithmetics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Normal domain distribution concentrator
 *
 * @author darklight
 */
public class NormIntervalConcentrator<T extends Number> implements Concentrator<T> {

	/** Arithmetics unit for given numeric type */
	private final Arithmetics<T> au;
	/** Name of the field this concentrator is concentrating */
	private final String fieldName;
	/** Number of intervals the A value domain should be split into */
	private final Integer intervalCount;
	/** Name of the field resulting of the aggregation */
	private final String canonicalAggregateName;

	private String [] intervals;
	private ArrayList<T> separators;
	private T collMin, collMax;

	public NormIntervalConcentrator(
			Arithmetics<T> au,
			String fieldName,
			Integer intervalCount
	) {
		this.au = au;
		this.fieldName = fieldName;
		this.intervalCount = intervalCount;
		this.canonicalAggregateName = String.format("norm(%s, %d)", fieldName, intervalCount);
	}

	@Override
	public void reduce(Map<String, Object> sourceRecord, Map<String, Object> targetRecord) {
		targetRecord.put(canonicalAggregateName, reduce((T) sourceRecord.get(fieldName)));
	}

	private String reduce(T value) {
		// TODO replace POC implementation with interval halving (or something constant-time)
		int i;
		for (i = 0; i < intervalCount && au.compare(value, separators.get(i + 1)) > 0; ++i);
		return intervals[i];
	}

	@Override
	public void initialize(Collection<Map<String, Object>> resultSet) {
		for (Map<String, Object> result: resultSet) {
			final T value = (T) result.get(fieldName);
			if (collMin == null || au.compare(collMin, value) > 0) collMin = value;
			if (collMax == null || au.compare(collMax, value) < 0) collMax = value;
		}

		// (collMax - collMin) / intervalCount, which may result in overflow/underflow
		// this is less precise, but not prone to above said, unless intervalCount is too small
		final T lInc = au.div(collMin, intervalCount);
		final T uInc = au.div(collMax, intervalCount);
		final T increment = au.sub(uInc, lInc);

		separators = new ArrayList<T>(intervalCount + 1);
		T lowerBound = collMin;
		for (int i = 0; i < intervalCount; ++i) {
			separators.add(lowerBound);
			lowerBound = au.add(lowerBound, increment);
		}
		separators.add(collMax);

		intervals = new String [intervalCount];
		for (int i = 0; i < intervalCount; ++i) {
			intervals[i] = String.format(
					"%s - %s",
					separators.get(i).toString(),
					separators.get(i + 1).toString()
			);
		}
	}

	@Override
	public void reset() {
		intervals = null;

		separators.clear();
		separators = null;

		collMin = collMax = null;
	}
}
