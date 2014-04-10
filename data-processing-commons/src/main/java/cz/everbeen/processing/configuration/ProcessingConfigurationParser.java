package cz.everbeen.processing.configuration;

import cz.cuni.mff.d3s.been.taskapi.Task;
import cz.cuni.mff.d3s.been.util.PropertyReader;
import cz.everbeen.processing.reflect.ReflectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Parser for common configuration parameters
 *
 * @author darklight
 */
public final class ProcessingConfigurationParser {

	private static final Logger log = LoggerFactory.getLogger(ProcessingConfigurationParser.class);

	private final Task task;

	/**
	 * Create the configuration parser for a task
	 * @param task Task being configured
	 */
	public ProcessingConfigurationParser(Task task) {
		this.task = task;
	}

	/**
	 * Parse the configuration of an export task, filling provided configuration object with values it understands
	 * @param config Configuration object to fill in
	 */
	public final void parseProcessingConfiguration(ProcessingConfiguration config) throws ConfigException {
		final PropertyReader propertyReader = task.createPropertyReader();
		loadProperties(config, propertyReader);
	}

	private void loadProperties(ProcessingConfiguration conf, PropertyReader propertyReader) throws ConfigException {

		final Class<? extends ProcessingConfiguration> confClass = conf.getClass();
		for (Field f: ReflectUtils.getDeclaredFieldsUpTo(confClass, Object.class)) {
			final TaskProperty prop;
			if ((prop = f.getAnnotation(TaskProperty.class)) != null) {
				loadProperty(prop, f, conf);
			}
		}
	}

	private void loadProperty(TaskProperty property, Field field, ProcessingConfiguration conf) throws ConfigException {

		final String propString = task.getTaskProperty(property.name());
		if (propString == null || "".equals(propString)) {
			// property not found in configuration - leave default if any
			return;
		}

		final boolean accessible = field.isAccessible();
		try {
			field.setAccessible(true);
			if (Integer.class.equals(field.getType())) {
				field.set(conf, Integer.valueOf(propString));
			}
			if (Long.class.equals(field.getType())) {
				field.set(conf, Long.valueOf(propString));
			}
			if (Float.class.equals(field.getType())) {
				field.set(conf, Float.valueOf(propString));
			}
			if (Double.class.equals(field.getType())) {
				field.set(conf, Double.valueOf(propString));
			}
			if (Date.class.equals(field.getType())) {
				final DateFormat format = SimpleDateFormat.getInstance();
				try {
					field.set(conf, format.parse(propString));
				} catch (ParseException e) {
					throw new ConfigException(String.format("Cannot parse date from property %s", property.name()), e);
				}
			}
			if (String.class.equals(field.getType())) {
				field.set(conf, propString);
			}
		} catch (IllegalAccessException e) {
			log.warn("Illegal access to configuration property {}", property.name(), e);
		} finally {
			field.setAccessible(accessible);
		}
	}
}
