package cz.everbeen.proc.chart;

import cz.cuni.mff.d3s.been.taskapi.ResultMapping;
import org.jfree.chart.JFreeChart;

import java.util.Collection;
import java.util.Map;

/**
 * @author darklight
 */
@Plot(type = "runSequence", dimensions = 2)
public class RunSequencePlot implements PlotGenerator {
	@Override
	public JFreeChart plot(Collection<Map<String, Object>> aggregatedResults, ResultMapping resultMapping) {
		// TODO
		return null;
	}
}
