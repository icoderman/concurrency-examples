package executors;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
The above example uses yet another type of executor created via newWorkStealingPool().
This factory method is part of Java 8 and returns an executor of type ForkJoinPool which works slightly different than normal executors.
Instead of using a fixed size thread-pool ForkJoinPools are created for a given parallelism size which per default is
the number of available cores of the hosts CPU.
ForkJoinPools exist since Java 7.
*/
public class InvokeAnyDemo {
	public static void main(String[] args) {
		ExecutorService executor = Executors.newWorkStealingPool();

		List<Callable<String>> callables = Arrays.asList(
				callable("task1", 2),
				callable("task2", 1),
				callable("task3", 3));

		String result = null;
		try {
			result = executor.invokeAny(callables);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		System.out.println(result);

	}

	private static Callable<String> callable(String result, long sleepSeconds) {
		return () -> {
			TimeUnit.SECONDS.sleep(sleepSeconds);
			return result;
		};
	}
}
