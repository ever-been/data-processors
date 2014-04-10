package cz.everbeen.processing.configuration;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Denote a {@link ProcessingConfiguration} field as a {@link cz.everbeen.processing.DataProcessor} property.
 *
 * The {@link #name()} is used as key for the retrieved property. The value assigned to the field stays as the default property value, if no value is found under given key.
 *
 * @author darklight
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TaskProperty {

	/**
	 * Name of the property
	 * @return Property name
	 */
	String name();
}
