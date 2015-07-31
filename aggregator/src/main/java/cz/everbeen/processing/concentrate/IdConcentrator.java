package cz.everbeen.processing.concentrate;

import cz.everbeen.processing.Concentrator;

import java.util.Collection;
import java.util.Map;

/**
 * @author darklight
 */
public class IdConcentrator<T> implements Concentrator<T> {

	private final String fieldName;

	IdConcentrator(String fieldName) {
		this.fieldName = fieldName.trim();
	}

	@Override
	public void reduce(Map<String, Object> sourceRecord, Map<String, Object> targetRecord) {
		targetRecord.put(fieldName, sourceRecord.get(fieldName));
	}

	@Override
	public void initialize(Collection<Map<String, Object>> resultSet) {
	}

	@Override
	public String getColName() {
		return fieldName;
	}

	@Override
	public void reset() {

	}
}
