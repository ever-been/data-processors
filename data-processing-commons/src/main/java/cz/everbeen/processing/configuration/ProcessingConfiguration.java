package cz.everbeen.processing.configuration;

import java.util.Date;

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
	@TaskProperty(name="from")
	private Date from;
	@TaskProperty(name="to")
	private Date to;
	@TaskProperty(name="groupId")
	private String groupId;
	@TaskProperty(name="taskWarmupSkew")
	private Long taskWarmupSkew;
	@TaskProperty(name="contextWarmupSkew")
	private Long contextWarmupSkew;
	@TaskProperty(name="fields")
	private String fields;

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

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Long getTaskWarmupSkew() {
		return taskWarmupSkew;
	}

	public void setTaskWarmupSkew(Long taskWarmupSkew) {
		this.taskWarmupSkew = taskWarmupSkew;
	}

	public Long getContextWarmupSkew() {
		return contextWarmupSkew;
	}

	public void setContextWarmupSkew(Long contextWarmupSkew) {
		this.contextWarmupSkew = contextWarmupSkew;
	}

	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
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

		sb.append("from = ");
		sb.append(from);
		sb.append(',');

		sb.append("to = ");
		sb.append(to);
		sb.append(',');

		sb.append("taskWarmupSkew = ");
		sb.append(taskWarmupSkew);
		sb.append(',');

		sb.append("contextWarmupSkew = ");
		sb.append(contextWarmupSkew);
		sb.append(',');

		sb.append("groupId = ");
		sb.append(groupId);
		sb.append(',');

		sb.append("fields = ");
		sb.append(fields);

		return sb.toString();
	}
}
