package cz.everbeen.testing.integration;

/**
 * Generator for dummy entries
 *
 * @author darklight
 */
public final class DummyDataFactory {

	/**
	 * Get an {@link cz.everbeen.testing.integration.Auth} entry
	 * @return A new auth entry
	 */
	public static Auth auth() {
		return Auth.random();
	}
}
