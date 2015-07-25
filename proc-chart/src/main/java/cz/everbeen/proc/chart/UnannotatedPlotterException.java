package cz.everbeen.proc.chart;

import cz.cuni.mff.d3s.been.taskapi.TaskException;

/**
 * Exception signaling an implementation of {@link cz.everbeen.proc.chart.PlotGenerator} was loaded that wasn't annotated using {@link cz.everbeen.proc.chart.Plot}
 *
 * @author darklight
 */
public class UnannotatedPlotterException extends TaskException {

	private final Class<? extends PlotGenerator> plotterClass;

	UnannotatedPlotterException(Class<? extends PlotGenerator> plotterClass) {
		super(String.format(
				"Plotter generator [%s] is not annotated using [%s] and will not be loaded",
				plotterClass.getCanonicalName(),
				Plot.class.getCanonicalName()
		));
		this.plotterClass = plotterClass;
	}

	/**
	 * Get the class of the plotter that was not annotated
	 * @return Unannotated plotter class
	 */
	public Class<? extends PlotGenerator> getPlotterClass() {
		return plotterClass;
	}
}
