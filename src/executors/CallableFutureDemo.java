package executors;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CallableFutureDemo {

	public static void main(String[] args) {
		Callable<Integer> task = () -> {
			try {
				TimeUnit.SECONDS.sleep(5);
				return 123;
			}
			catch (InterruptedException e) {
				throw new IllegalStateException("task interrupted", e);
			}
		};

		/*
		Since submit() doesn't wait until the task completes, the executor service cannot return the result of the callable directly.
		Instead the executor returns a special result of type Future which can be used to retrieve the actual result at a later point in time.
		 */
		ExecutorService executor = Executors.newFixedThreadPool(1);
		Future<Integer> future = executor.submit(task);

		System.out.println("future done? " + future.isDone());

		Integer result = null;
		try {
			// Calling the method get() blocks the current thread and waits until the callable completes before returning the actual result
			//result = future.get();
			result = future.get(1, TimeUnit.SECONDS); // waiting with timeout
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			e.printStackTrace();
		}

		System.out.println("future done? " + future.isDone());
		System.out.print("result: " + result);

		/*
		Futures are tightly coupled to the underlying executor service.
		Keep in mind that every non-terminated future will throw exceptions if you shutdown the executor:
		executor.shutdownNow();
		future.get();

		 */
	}

}
