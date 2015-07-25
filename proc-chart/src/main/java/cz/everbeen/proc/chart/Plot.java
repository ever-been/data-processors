package cz.everbeen.proc.chart;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation denoting a plot implementation
 *
 * @author darklight
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Plot {

	/**
	 * Type of the plot to recognize
	 * @return Type of the plot
	 */
	String type();

	/**
	 * @return Number of the plot's dimensions
	 */
	int dimensions();
}
