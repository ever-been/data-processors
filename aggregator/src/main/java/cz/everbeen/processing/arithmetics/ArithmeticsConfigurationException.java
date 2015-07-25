package cz.everbeen.processing.arithmetics;

import cz.cuni.mff.d3s.been.taskapi.TaskException;

/**
 * Exception indicating that the task could not instantiate an arithemtics unit with matching type.
 *
 * @author darklight
 */
public class ArithmeticsConfigurationException extends TaskException {

	public ArithmeticsConfigurationException(String message) {
		super(message);
	}
}
