package cz.everbeen.processing.concentrate;

import cz.everbeen.processing.DataProcessingException;

/**
 * Exception thrown to signal a fault in concentrator selection logic.
 *
 * @author darklight
 */
public class ConcentratorSetupException extends DataProcessingException {

	public ConcentratorSetupException(String message) {
		super(message);
	}

	public ConcentratorSetupException(String message, Throwable cause) {
		super(message, cause);
	}
}
