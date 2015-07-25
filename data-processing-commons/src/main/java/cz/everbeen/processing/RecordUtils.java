package cz.everbeen.processing;

import sun.misc.BASE64Encoder;

import java.util.Map;
import java.util.SortedMap;

/**
 * Utility facade for data record manipulation
 *
 * @author darklight
 */
public final class RecordUtils {

	private final BASE64Encoder b64;

	private RecordUtils() {
		this.b64 = new BASE64Encoder();
	}

	/**
	 * Create a new instance of the utility class
	 *
	 * @return A fresh record utils instance
	 */
	public static RecordUtils newInstance() {
		return new RecordUtils();
	}

	/**
	 * Convert a record into a value-unique key.
	 *
	 * @param record Record to convert
	 *
	 * @return Key for given record
	 */
	public String flatRecordToKey(SortedMap<String, Object> record) {
		final StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, Object> field: record.entrySet()) {
			if (sb.length() > 0) sb.append(',');
			sb
					.append(b64.encode(field.getKey().getBytes()))
					.append('=')
					.append(b64.encode(String.valueOf(field.getValue()).getBytes()));
		}
		return sb.toString();
	}
}
