package cz.everbeen.processing;


import cz.cuni.mff.d3s.been.taskapi.TaskException;

/**
 * General data processing exception
 *
 * @author darklight
 */
public class DataProcessingException extends TaskException {

	/**
	 * Data processing exception with a message
	 *
	 * @param message Message to propagate
	 */
	public DataProcessingException(String message) {
		super(message);
	}

	/**
	 * Data processing exception with a message and a cause
	 *
	 * @param message Message to propagate
	 * @param cause Cause of this exception
	 */
	public DataProcessingException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Data processing exception with a cause
	 *
	 * @param cause Cause of this exception
	 */
	public DataProcessingException(Throwable cause) {
		super(cause);
	}
}
