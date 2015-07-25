package cz.everbeen.proc.chart;

import cz.cuni.mff.d3s.been.evaluators.EvaluatorResult;
import cz.cuni.mff.d3s.been.taskapi.ResultMapping;
import cz.everbeen.processing.DataProcessingException;
import cz.everbeen.processing.DataProcessorLogic;
import cz.everbeen.processing.configuration.ProcessingConfiguration;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * Task that exports selected data to JSON.
 *
 * @author darklight
 */
public class ChartCreatorLogic implements DataProcessorLogic {

	private static final SimpleDateFormat FILENAME_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS");

	@Override
	public String getName() {
		return "process-chart";
	}

	@Override
	public ProcessingConfiguration createConfig() {
		return new PlotterConfig();
	}

	@Override
	public EvaluatorResult process(Collection<Map<String, Object>> results, ResultMapping mapping, ProcessingConfiguration conf) throws DataProcessingException {

		if (!(conf instanceof PlotterConfig)) throw new DataProcessingException(String.format(
				"Configuration is of the wrong type, got [%s] but expected [%s]",
				conf == null ? "null" : conf.getClass().getCanonicalName(),
				PlotterConfig.class.getCanonicalName()
		));

		final PlotGeneratorRegistry plotGeneratorRegistry = new PlotGeneratorRegistry();
		try {
			plotGeneratorRegistry.init();
		} catch (PlotterConfigurationException pce) {
			throw new DataProcessingException("Failed to initialize plotter configuration", pce);
		}
		final PlotGenerator pGen;
		try {
			pGen = plotGeneratorRegistry.loadGenerator(((PlotterConfig) conf).getChartType());
		} catch (PlotterConfigurationException pce) {
			throw new DataProcessingException(String.format(
					"Could not find plotter implementation for plot type [%s]",
					((PlotterConfig) conf).getChartType()
			));
		}

		final ByteArrayOutputStream bao = new ByteArrayOutputStream();
		try {
			bao.close();
		} catch (IOException e) {
			throw new DataProcessingException("Failed to flush result byte buffer", e);
		}


		final Date creationDate = new Date();
		final EvaluatorResult evaluatorResult = new EvaluatorResult();
		evaluatorResult.setData(bao.toByteArray());
		evaluatorResult.setMimeType(EvaluatorResult.MIME_TYPE_IMAGE_PNG);
		evaluatorResult.setTimestamp(creationDate.getTime());
		evaluatorResult.setFilename("process-chart_" + FILENAME_DATE_FORMAT.format(creationDate) + ".png");
		return evaluatorResult;
	}
}
