package cz.everbeen.proc.chart;

import org.jfree.data.general.Dataset;

import java.util.Collection;
import java.util.Map;

/**
 * Utility facade that implements parsing user-typed benchmark data into JFreeChart datasets
 *
 * @author darklight
 */
class DatasetFactory {

	/**
	 * Convert results into a plottable dataset
	 *
	 * @param results Typed results
	 * @param plotterConfig Plotter configuration
	 * @param plot Target plot metadata
	 *
	 * @return A JFreeChart dataset created from result data
	 */
	Dataset datasetFromMap(
			Collection<Map<String, Object>> results,
			PlotterConfig plotterConfig,
			Plot plot
	) {
		return null;
	}
}
