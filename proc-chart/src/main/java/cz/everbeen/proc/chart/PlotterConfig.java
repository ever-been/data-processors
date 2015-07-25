package cz.everbeen.proc.chart;

import cz.everbeen.processing.configuration.ProcessingConfiguration;
import cz.everbeen.processing.configuration.TaskProperty;

/**
 * Generic plotter setup.
 *
 * Serves for grouping settings specific to plotter logics.
 *
 * @author darklight
 */
public class PlotterConfig extends ProcessingConfiguration {

	/** Type of the chart to draw */
	@TaskProperty(name = "chartType")
	private String chartType;

	/** Title of the chart */
	@TaskProperty(name = "chartTitle")
	private String chartTitle;

	/** Rendered image px width */
	@TaskProperty(name = "chartWidth")
	private int width;
	/** Rendered image px height */
	@TaskProperty(name = "chartHeight")
	private int height;

	/** Name of the variable on x axis */
	@TaskProperty(name = "xAxisVariableName")
	private String xAxisVarName;
	/** Name of the variable on y axis */
	@TaskProperty(name = "yAxisVariableName")
	private String yAxisVarName;
	/** Name of the variable on z axis */
	@TaskProperty(name = "zAxisVariableName")
	private String zAxisVarName;

	/** Label on the x axis */
	@TaskProperty(name = "xAxisLabel")
	private String xAxisLabel;
	/** Label on the y axis */
	@TaskProperty(name = "yAxisLabel")
	private String yAxisLabel;
	/** Label on the z axis */
	@TaskProperty(name = "zAxisLabel")
	private String zAxisLabel;

	public String getChartTitle() {
		return chartTitle;
	}

	public void setChartTitle(String chartTitle) {
		this.chartTitle = chartTitle;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getxAxisVarName() {
		return xAxisVarName;
	}

	public void setxAxisVarName(String xAxisVarName) {
		this.xAxisVarName = xAxisVarName;
	}

	public String getyAxisVarName() {
		return yAxisVarName;
	}

	public void setyAxisVarName(String yAxisVarName) {
		this.yAxisVarName = yAxisVarName;
	}

	public String getzAxisVarName() {
		return zAxisVarName;
	}

	public void setzAxisVarName(String zAxisVarName) {
		this.zAxisVarName = zAxisVarName;
	}

	public String getxAxisLabel() {
		return xAxisLabel;
	}

	public void setxAxisLabel(String xAxisLabel) {
		this.xAxisLabel = xAxisLabel;
	}

	public String getyAxisLabel() {
		return yAxisLabel;
	}

	public void setyAxisLabel(String yAxisLabel) {
		this.yAxisLabel = yAxisLabel;
	}

	public String getzAxisLabel() {
		return zAxisLabel;
	}

	public void setzAxisLabel(String zAxisLabel) {
		this.zAxisLabel = zAxisLabel;
	}

	public String getChartType() {
		return chartType;
	}

	public void setChartType(String chartType) {
		this.chartType = chartType;
	}
}
