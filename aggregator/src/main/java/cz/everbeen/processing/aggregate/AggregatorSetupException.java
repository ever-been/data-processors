package cz.everbeen.processing.aggregate;

import cz.everbeen.processing.DataProcessingException;

/**
 * Exception thrown to signal a fault in aggregator selection logic.
 *
 * @author darklight
 */
public class AggregatorSetupException extends DataProcessingException {
	public AggregatorSetupException(String message) {
		super(message);
	}

	public AggregatorSetupException(String message, Throwable cause) {
		super(message, cause);
	}

}
