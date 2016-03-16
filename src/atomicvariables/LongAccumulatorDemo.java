package atomicvariables;

import synchronization.ConcurrentUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.function.LongBinaryOperator;
import java.util.stream.IntStream;

/*
LongAccumulator is a more generalized version of LongAdder. Instead of performing simple add operations the class
LongAccumulator builds around a lambda expression of type LongBinaryOperator as demonstrated in this code sample:
 */
public class LongAccumulatorDemo {

	public static void main(String[] args) {
		testAccumulate();
	}

	private static void testAccumulate() {
		LongBinaryOperator op = (x, y) -> 2 * x + y;
		LongAccumulator accumulator = new LongAccumulator(op, 1L);

		ExecutorService executor = Executors.newFixedThreadPool(2);

		IntStream.range(0, 10)
				.forEach(i -> executor.submit(() -> accumulator.accumulate(i)));

		ConcurrentUtils.stop(executor);

		System.out.format("Add: %d\n", accumulator.getThenReset());
	}
}
