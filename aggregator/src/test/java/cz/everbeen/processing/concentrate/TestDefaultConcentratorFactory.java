package cz.everbeen.processing.concentrate;

import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Test for {@link cz.everbeen.processing.concentrate.DefaultConcentratorFactory}
 *
 * @author darklight
 */
public class TestDefaultConcentratorFactory {

	@Test
	public void testNormPattern() {
		final Pattern pattern = Pattern.compile(DefaultConcentratorFactory.NORM_REGEX);
		final Matcher matcher = pattern.matcher("norm(myVar,10)");
		Assert.assertTrue(matcher.matches());
		Assert.assertEquals("myVar", matcher.group(1));
		Assert.assertEquals("10", matcher.group(2));
	}

	@Test
	public void testIdPattern() {
		final Pattern pattern = Pattern.compile(DefaultConcentratorFactory.ID_REGEX);
		final Matcher matcher = pattern.matcher("ukulele");
		Assert.assertTrue(matcher.matches());
		Assert.assertEquals("ukulele", matcher.group(1));
	}
}
