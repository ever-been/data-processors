package cz.everbeen.proc.chart;

import cz.cuni.mff.d3s.been.taskapi.TaskException;

/**
 * Exception indicating an error in plotter execution
 *
 * @author darklight
 */
public class PlotterException extends TaskException {

	public PlotterException(String message) {
		super(message);
	}
}
