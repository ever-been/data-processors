package cz.everbeen.processing.configuration;

import cz.everbeen.processing.DataProcessingException;

/**
 * This exception means that something went wrong during the configuration of an export task.
 *
 * @author darklight
 */
public class ConfigException extends DataProcessingException {

	/**
	 * Configuration exception with a message
	 *
	 * @param message Message to propagate
	 */
	ConfigException(String message) {
		super(message);
	}

	/**
	 * Configuration exception with a message and a cause
	 *
	 * @param message Message to propagate
	 * @param cause Cause to propagate
	 */
	ConfigException(String message, Throwable cause) {
		super(message, cause);
	}
}
