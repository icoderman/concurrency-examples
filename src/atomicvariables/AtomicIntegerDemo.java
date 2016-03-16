package atomicvariables;

import synchronization.ConcurrentUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class AtomicIntegerDemo {
	private static final int NUM_INCREMENTS = 1000;

	private static AtomicInteger atomicInt = new AtomicInteger(0);

	public static void main(String[] args) {
		testIncrement();
		testAccumulate();
		testUpdate();
	}

	/*
	AtomicInteger supports various kinds of atomic operations. The method updateAndGet() accepts a lambda expression
	in order to perform arbitrary arithmetic operations upon the integer:
	 */
	private static void testUpdate() {
		atomicInt.set(0);

		ExecutorService executor = Executors.newFixedThreadPool(2);

		IntStream.range(0, NUM_INCREMENTS)
				.forEach(i -> {
					Runnable task = () ->
							atomicInt.updateAndGet(n -> n + 2);
					executor.submit(task);
				});

		ConcurrentUtils.stop(executor);

		System.out.format("Update: %d\n", atomicInt.get());
	}

	/*
	The method accumulateAndGet() accepts another kind of lambda expression of type IntBinaryOperator.
	We use this method to sum up all values from 0 to 1000 concurrently in the next sample:
	 */
	private static void testAccumulate() {
		atomicInt.set(0);

		ExecutorService executor = Executors.newFixedThreadPool(2);

		IntStream.range(0, NUM_INCREMENTS)
				.forEach(i -> {
					Runnable task = () ->
							atomicInt.accumulateAndGet(i, (n, m) -> n + m);
					executor.submit(task);
				});

		ConcurrentUtils.stop(executor);

		System.out.format("Accumulate: %d\n", atomicInt.get());
	}

	private static void testIncrement() {
		atomicInt.set(0);

		ExecutorService executor = Executors.newFixedThreadPool(2);

		IntStream.range(0, NUM_INCREMENTS)
				.forEach(i -> executor.submit(atomicInt::incrementAndGet));

		ConcurrentUtils.stop(executor);

		System.out.format("Increment: Expected=%d; Is=%d\n", NUM_INCREMENTS, atomicInt.get());
	}
}
