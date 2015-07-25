package cz.everbeen.processing.arithmetics;

import java.util.Comparator;

/**
 * Arithmetic unit for Number subtypes.
 *
 * @author darklight
 *
 * @param <T> Numeric subtype to implement arithmetics for
 */
public interface Arithmetics<T extends Number> extends Comparator<T> {

	/**
	 * Add two numbers
	 *
	 * @param a Operand 1
	 * @param b Operand 2
	 *
	 * @return Sum of <code>a</code> and <code>b</code>
	 */
	T add(T a, T b);

	/**
	 * Subtract two numbers
	 *
	 * @param a Operand 1
	 * @param b Operand 2
	 *
	 * @return <code>a - b</code>
	 */
	T sub(T a, T b);

	/**
	 * Divide generic operand by an int
	 *
	 * @param total Dividend
	 * @param count Divider
	 *
	 * @return Division result
	 */
	T div(T total, int count);
}
