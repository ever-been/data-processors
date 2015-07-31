package cz.everbeen.processing.configuration;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * Test for utility methods on {@link cz.everbeen.processing.configuration.ProcessingConfiguration}
 *
 * @author darklight
 */
public class ProcessingConfigurationTest {

	private final ProcessingConfiguration pConf = new ProcessingConfiguration();

	@Test
	public void testBarsplit_one() throws ConfigException {
		Assert.assertEquals("a", pConf.barsplit("a")[0]);
		Assert.assertEquals("a", pConf.barsplit(" a ")[0]);
		Assert.assertEquals("a -> b.c", pConf.barsplit("a -> b.c")[0]);
	}

	@Test
	public void testArrowsplit_one() throws ConfigException {
		final Map<String, String> split = pConf.arrowsplit(new String[] {"a -> b.c"});
		Assert.assertEquals("b.c", split.get("a"));
	}
}
