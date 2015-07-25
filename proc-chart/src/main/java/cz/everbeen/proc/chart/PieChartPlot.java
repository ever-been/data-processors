package cz.everbeen.proc.chart;

import cz.cuni.mff.d3s.been.taskapi.ResultMapping;
import org.jfree.chart.JFreeChart;

import java.util.Collection;
import java.util.Map;

/**
 * Plot a pie chart of relative numeric frequencies over enum values
 *
 * @author darklight
 */
@Plot(type = "pieChart", dimensions = 2)
public class PieChartPlot implements PlotGenerator {
	@Override
	public JFreeChart plot(Collection<Map<String, Object>> aggregatedResults, ResultMapping resultMapping) {
		// TODO
		return null;
	}
}
