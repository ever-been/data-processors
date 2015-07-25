package cz.everbeen.processing.aggregate;

import cz.everbeen.processing.configuration.ProcessingConfiguration;
import cz.everbeen.processing.configuration.TaskProperty;

/**
 * Configuration of an aggregator task.
 *
 * @author darklight
 */
public class AggregatorConfiguration extends ProcessingConfiguration {

	@TaskProperty(name = "aggregatorFields")
	private String aggregatorFields;
	@TaskProperty(name = "concentratorFields")
	private String concentratorFields;

	public String [] getAggregatorFields() {
		return barSplit(aggregatorFields);
	}

	public String [] getConcentratorFields() {
		return barSplit(concentratorFields);
	}

	public void setAggregatorFields(String aggregatorFields) {
		this.aggregatorFields = aggregatorFields;
	}

	public void setConcentratorFields(String concentratorFields) {
		this.concentratorFields = concentratorFields;
	}

	private String [] barSplit(String separatedValues) {
		final String [] unkemptFields = separatedValues.split("\\|");
		final String [] trimmedFields = new String[unkemptFields.length];
		for (int i = 0; i < unkemptFields.length; ++i) {
			trimmedFields[ i] = unkemptFields[ i].trim();
		}
		return trimmedFields;
	}
}
