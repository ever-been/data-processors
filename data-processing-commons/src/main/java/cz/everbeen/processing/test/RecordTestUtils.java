package cz.everbeen.processing.test;

import java.util.*;

/**
 * Test utils for record creation / manipulation
 *
 * @author darklight
 */
public final class RecordTestUtils {

	private RecordTestUtils() {}

	public static RecordTestUtils newInstance() {
		return new RecordTestUtils();
	}

	public RecordBuilder recordBuilder() {
		return new RecordBuilder();
	}

	public Collection<Map<String, Object>> recordSet(Map<String, Object>... records) {
		final Collection<Map<String, Object>> recordSet = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < records.length; ++i) recordSet.add(records[ i]);
		return recordSet;
	}

	public class RecordBuilder {
		private final SortedMap<String, Object> record;

		private RecordBuilder() {
			this.record = new TreeMap<String, Object>();
		}

		/**
		 * Add field to built record
		 *
		 * @param key Field name
		 * @param value Field value
		 *
		 * @return Record builder, after completing the add
		 */
		public RecordBuilder addField(String key, Object value) {
			record.put(key, value);
			return this;
		}

		/**
		 * Retrieve record under construction
		 *
		 * @return Record
		 */
		public SortedMap<String, Object> build() {
			return record;
		}
	}
}
