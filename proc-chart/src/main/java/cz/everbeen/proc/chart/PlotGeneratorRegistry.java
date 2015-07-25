package cz.everbeen.proc.chart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * Plot generator reflection factory
 *
 * @author darklight
 */
class PlotGeneratorRegistry {

	private final Map<String, PlotGenerator> plotters = new TreeMap<String, PlotGenerator>();

	/**
	 * Scan the service loader for provides of the {@link cz.everbeen.proc.chart.PlotGenerator} interface and register them.
	 * @throws cz.everbeen.proc.chart.PlotterConfigurationException When some of the loaded plotters were unannotated
	 */
	void init() throws PlotterConfigurationException {
		final ServiceLoader<PlotGenerator> pGenSLoader = ServiceLoader.load(PlotGenerator.class);

		final Iterator<PlotGenerator> pgIt = pGenSLoader.iterator();
		final List<UnannotatedPlotterException> annotationErrors = new LinkedList<UnannotatedPlotterException>();
		while (pgIt.hasNext()) {
			try {
				registerPlotter(pgIt.next());
			} catch (UnannotatedPlotterException e) {
				annotationErrors.add(e);
			}
		}
		if (!annotationErrors.isEmpty()) {
			final StringBuilder sb = new StringBuilder();
			for (UnannotatedPlotterException upex: annotationErrors) {
				if (sb.length() > 0)
					sb.append(", ");
				sb.append(upex.getPlotterClass().getCanonicalName());
			}
			throw new PlotterConfigurationException(String.format("Plotters [%s] were not annotated", sb.toString()));
		}
	}

	PlotGenerator loadGenerator(String plotType) throws PlotterConfigurationException {
		final PlotGenerator generator = plotters.get(plotType);
		if (generator == null) throw new PlotterConfigurationException(String.format(
				"Unable to find plot generator of type [%s]",
				plotType
		));
		return generator;
	}

	private void registerPlotter(PlotGenerator plotGenerator) throws UnannotatedPlotterException {
		Plot plotAnnotation = null;
		for (Annotation a: plotGenerator.getClass().getDeclaredAnnotations()) {
			if (a instanceof Plot) {
				plotAnnotation = (Plot) a;
				break;
			}
		}
		if (plotAnnotation == null) throw new UnannotatedPlotterException(plotGenerator.getClass());
		plotters.put(plotAnnotation.type(), plotGenerator);
	}
}
