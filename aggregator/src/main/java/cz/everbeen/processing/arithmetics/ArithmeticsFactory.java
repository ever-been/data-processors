package cz.everbeen.processing.arithmetics;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Factory for {@link cz.everbeen.processing.arithmetics.Arithmetics} implementations
 *
 * @author darklight
 */
public final class ArithmeticsFactory {

	public static Arithmetics newInstance(Class<? extends Number> numberClass) throws ArithmeticsConfigurationException {

		if (numberClass == null) throw new ArithmeticsConfigurationException("No arithmetic type");

		if (Integer.class.equals(numberClass)) {
			return new IntArithmetics();
		}
		if (Long.class.equals(numberClass)) {
			return new LongArithmetics();
		}
		if (BigInteger.class.equals(numberClass)) {
			return new BigIntArithmetics();
		}
		if (Float.class.equals(numberClass)) {
			return new FloatArithmetics();
		}
		if (Double.class.equals(numberClass)) {
			return new DoubleArithmetics();
		}
		if (BigDecimal.class.equals(numberClass)) {
			return new BigDecArithmetics();
		}
		throw new ArithmeticsConfigurationException(String.format(
				"Unknown arithmetic type [%s]",
				numberClass.getCanonicalName()
		));
	}

	static class IntArithmetics implements Arithmetics<Integer> {
		@Override
		public Integer add(Integer a, Integer b) {
			return a + b;
		}

		@Override
		public Integer sub(Integer a, Integer b) {
			return a - b;
		}

		@Override
		public Integer div(Integer total, int count) {
			return total / count;
		}

		@Override
		public int compare(Integer a, Integer b) {
			return Integer.compare(a, b);
		}
	}

	static class LongArithmetics implements Arithmetics<Long> {
		@Override
		public Long add(Long a, Long b) {
			return a + b;
		}

		@Override
		public Long sub(Long a, Long b) {
			return a - b;
		}

		@Override
		public Long div(Long total, int count) {
			return total / Long.valueOf(count);
		}

		@Override
		public int compare(Long a, Long b) {
			return Long.compare(a, b);
		}
	}

	static class BigIntArithmetics implements Arithmetics<BigInteger> {
		@Override
		public BigInteger add(BigInteger a, BigInteger b) {
			return a.add(b);
		}

		@Override
		public BigInteger sub(BigInteger a, BigInteger b) {
			return a.subtract(b);
		}

		@Override
		public BigInteger div(BigInteger total, int count) {
			return total.divide(BigInteger.valueOf(count));
		}

		@Override
		public int compare(BigInteger a, BigInteger b) {
			return a.compareTo(b);
		}
	}

	static class FloatArithmetics implements Arithmetics<Float> {
		@Override
		public Float add(Float a, Float b) {
			return a + b;
		}

		@Override
		public Float sub(Float a, Float b) {
			return a - b;
		}

		@Override
		public Float div(Float total, int count) {
			return total / count;
		}

		@Override
		public int compare(Float a, Float b) {
			return Float.compare(a, b);
		}
	}

	static class DoubleArithmetics implements Arithmetics<Double> {
		@Override
		public Double add(Double a, Double b) {
			return a + b;
		}

		@Override
		public Double sub(Double a, Double b) {
			return a - b;
		}

		@Override
		public Double div(Double total, int count) {
			return total / count;
		}

		@Override
		public int compare(Double a, Double b) {
			return Double.compare(a, b);
		}
	}

	static class BigDecArithmetics implements Arithmetics<BigDecimal> {
		@Override
		public BigDecimal add(BigDecimal a, BigDecimal b) {
			return a.add(b);
		}

		@Override
		public BigDecimal sub(BigDecimal a, BigDecimal b) {
			return a.subtract(b);
		}

		@Override
		public BigDecimal div(BigDecimal total, int count) {
			return total.divide(total, count);
		}

		@Override
		public int compare(BigDecimal a, BigDecimal b) {
			return a.compareTo(b);
		}
	}
}
