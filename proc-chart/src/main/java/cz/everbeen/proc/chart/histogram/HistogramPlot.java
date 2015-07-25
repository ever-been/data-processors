package cz.everbeen.proc.chart.histogram;

import cz.cuni.mff.d3s.been.taskapi.ResultMapping;
import cz.everbeen.proc.chart.Plot;
import cz.everbeen.proc.chart.PlotGenerator;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;

import java.util.Collection;
import java.util.Map;

/**
 * Plot a histogram out of numeric frequencies of enum values
 *
 * @author darklight
 */
@Plot(type = "histogram", dimensions = 2)
public class HistogramPlot implements PlotGenerator {

	private final String categorySpanKey;
	private final String [] colValueKeys;

	// TODO add params
	public HistogramPlot(
			String categorySpanKey,
			String [] colValueKeys
	) {
		this.categorySpanKey = categorySpanKey;
		this.colValueKeys = colValueKeys;
	}

	@Override
	public JFreeChart plot(Collection<Map<String, Object>> results, ResultMapping resultMapping) {
		return null;
	}

	private DefaultCategoryDataset createDataset(Collection<Map<String, Object>> results) {
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (Map<String, Object> oMap: results) {
			for (String valueKey: colValueKeys) {
				addDataSetValue(dataset, oMap.get(valueKey), oMap.get(valueKey), oMap.get(categorySpanKey));
			}
		}
		return dataset;
	}

	/**
	 * Add a single value to dataset
	 * @param dataset Dataset to add to
	 * @param value Value (bar height)
	 * @param valueKey Bar key (spans over all values measured for one category)
	 * @param category Category (bar group)
	 */
	private void addDataSetValue(DefaultCategoryDataset dataset, Object value, Object valueKey, Object category) {
		dataset.addValue(
				(Number) value,
				String.valueOf(valueKey),
				String.valueOf(category)
		);
	}
}
