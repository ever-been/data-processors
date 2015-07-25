package cz.everbeen.processing.aggregate;

import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Test for {@link cz.everbeen.processing.aggregate.DefaultAggregatorFactory}.
 *
 * @author darklight
 */
public class TestDefaultAggregatorFactory {

	@Test
	public void testSumPattern() {
		final Pattern pattern = Pattern.compile(DefaultAggregatorFactory.SUM_REGEX);
		final Matcher matcher = pattern.matcher("sum(cvar)");
		Assert.assertTrue(matcher.matches());
		Assert.assertEquals("cvar", matcher.group(1));
	}
}
