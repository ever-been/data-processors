package cz.everbeen.processing.concentrate;

import cz.everbeen.processing.arithmetics.ArithmeticsConfigurationException;
import cz.everbeen.processing.arithmetics.ArithmeticsFactory;
import cz.everbeen.processing.test.RecordTestUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * Algorithm test for {@link cz.everbeen.processing.concentrate.NormIntervalConcentrator}
 *
 * @author darklight
 */
public class TestNormIntervalConcentrator {

	private final RecordTestUtils rtu;

	private NormIntervalConcentrator<Integer> c;

	public TestNormIntervalConcentrator() {
		this.rtu = RecordTestUtils.newInstance();
	}

	@Before
	public void setUpConcentrator() throws ArithmeticsConfigurationException {
	}

	@After
	public void tearDownConcentrator() {
	}

	@Test
	public void testABConcentration() throws ArithmeticsConfigurationException {
		c = new NormIntervalConcentrator<Integer>(
				ArithmeticsFactory.newInstance(Integer.class),
				"a",
				2
		);
		final Map<String, Object> r1, r2, r3;
		r1 = rtu.recordBuilder().addField("a", 1).addField("b", 10).build();
		r2 = rtu.recordBuilder().addField("a", 1).addField("b", 16).build();
		r3 = rtu.recordBuilder().addField("a", 3).addField("b", 8).build();

		final Collection<Map<String, Object>> recordSet = rtu.recordSet(r1, r2, r3);
		c.initialize(recordSet);

		final Map<String, Object> rr1, rr2, rr3;
		rr1 = new TreeMap<String, Object>();
		rr2 = new TreeMap<String, Object>();
		rr3 = new TreeMap<String, Object>();

		final String cColName = "norm(a, 2)";
		c.reduce(r1, rr1);
		Assert.assertEquals("1 - 2", rr1.get(cColName));
		c.reduce(r2, rr2);
		Assert.assertEquals("1 - 2", rr2.get(cColName));
		c.reduce(r3, rr3);
		Assert.assertEquals("2 - 3", rr3.get(cColName));
	}
}
