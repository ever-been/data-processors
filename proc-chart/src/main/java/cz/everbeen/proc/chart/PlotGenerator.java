package cz.everbeen.proc.chart;

import cz.cuni.mff.d3s.been.taskapi.ResultMapping;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.Dataset;

import java.util.Collection;
import java.util.Map;

/**
 * Protocol for dimensionless plot creation.
 * Implementors should be instantiable by a parameterless constructor.
 *
 * @author darklight
 */
public interface PlotGenerator {

	/**
	 * Plot data into a JFree Chart
	 *
	 * @param aggregatedResults Data to process
	 * @param resultMapping Result type mapping; introduced for type verification
	 *
	 * @return A JFree Chart plot
	 */
	JFreeChart plot(Collection<Map<String, Object>> aggregatedResults, ResultMapping resultMapping);
}
