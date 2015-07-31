package cz.everbeen.processing.configuration;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * Generic configuration for an export task
 *
 * @author darklight
 */
public class ProcessingConfiguration {

	@TaskProperty(name="taskId")
	private String taskId;
	@TaskProperty(name="contextId")
	private String contextId;
	@TaskProperty(name="benchmarkId")
	private String benchmarkId;
	@TaskProperty(name="dateFrom")
	private Date dateFrom;
	@TaskProperty(name="dateTo")
	private Date dateTo;
	@TaskProperty(name="groupId")
	private String groupId;
	@TaskProperty(name="typeMapping")
	private String typeMapping;
	@TaskProperty(name = "aliasMapping")
	private String aliasMapping;
	@TaskProperty(name = "datasetId")
	private String datasetId;

	public String getDatasetId() {
		return datasetId;
	}

	public void setDatasetId(String datasetId) {
		this.datasetId = datasetId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getContextId() {
		return contextId;
	}

	public void setContextId(String contextId) {
		this.contextId = contextId;
	}

	public String getBenchmarkId() {
		return benchmarkId;
	}

	public void setBenchmarkId(String benchmarkId) {
		this.benchmarkId = benchmarkId;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getTypeMapping() {
		return typeMapping;
	}

	public void setTypeMapping(String typeMapping) {
		this.typeMapping = typeMapping;
	}

	public String getAliasMapping() {
		return aliasMapping;
	}

	public void setAliasMapping(String aliasMapping) {
		this.aliasMapping = aliasMapping;
	}

	public Map<String, String> parseTypeMapping() throws ConfigException {
		if (typeMapping == null || "".equals(typeMapping)) return new TreeMap<String, String>();
		return arrowsplit(barsplit(typeMapping));
	}

	public Map<String, String> parseAliasMapping() throws ConfigException {
		if (aliasMapping == null || "".equals(aliasMapping)) return new TreeMap<String, String>();
		return arrowsplit(barsplit(aliasMapping));
	}


	public boolean isLoadingDataset() {
		return datasetId != null;
	}

	public String toString() {
		final StringBuilder sb = new StringBuilder();

		sb.append("benchmarkId = ");
		sb.append(benchmarkId);
		sb.append(',');

		sb.append("contextId = ");
		sb.append(contextId);
		sb.append(',');

		sb.append("taskId = ");
		sb.append(taskId);
		sb.append(',');

		sb.append("dateFrom = ");
		sb.append(dateFrom);
		sb.append(',');

		sb.append("dateTo = ");
		sb.append(dateTo);
		sb.append(',');

		sb.append("groupId = ");
		sb.append(groupId);
		sb.append(',');

		sb.append("typeMapping = ");
		sb.append(typeMapping);
		sb.append(',');

		sb.append("aliasMapping = ");
		sb.append(aliasMapping);

		return sb.toString();
	}

	String [] barsplit(String barSeparatedText) throws ConfigException {
		final String [] segments = barSeparatedText.split("\\|");
		for (int i = 0; i < segments.length; ++i) segments[i] = segments[i].trim();
		return segments;
	}

	Map<String, String> arrowsplit(String [] mappings) throws ConfigException {
		final Map<String, String> map = new TreeMap<String, String>();
		for (String mapping: mappings) {
			final String [] lr = mapping.split("->");
			if (lr.length != 2) throw new ConfigException(String.format(
					"Illegal mapping: [%s]",
					mapping
			));
			map.put(lr[0].trim(), lr[1].trim());
		}
		return map;
	}
}
