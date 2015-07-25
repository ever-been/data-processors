package cz.everbeen.proc.chart;

import cz.cuni.mff.d3s.been.taskapi.TaskException;

/**
 * Exception signaling something went wrong when picking the plotter to visualise aggregated data
 *
 * @author darklight
 */
public class PlotterConfigurationException extends TaskException {

	public PlotterConfigurationException(String message) {
		super(message);
	}

	public PlotterConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}
}
