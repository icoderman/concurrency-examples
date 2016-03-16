package executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorShutdownDemo {
	public static void main(String[] args) {

		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.submit(() -> {
			String threadName = Thread.currentThread().getName();
			try {
				System.out.println("I'm going to wait a minute...");
				TimeUnit.MINUTES.sleep(1);
			} catch (InterruptedException e) {
				System.err.println("I was terminated..");
			}
			System.out.println("Hello " + threadName);
		});

		/*
			Typical shutdown template

			The executor shuts down softly by waiting a certain amount of time for termination of currently running tasks.
			After a maximum of five seconds the executor finally shuts down by interrupting all running tasks.
		 */
		try {
			System.out.println("attempt to shutdown executor");
			executor.shutdown();
			executor.awaitTermination(5, TimeUnit.SECONDS);
		}
		catch (InterruptedException e) {
			System.err.println("tasks interrupted");
		}
		finally {
			if (!executor.isTerminated()) {
				System.err.println("cancel non-finished tasks");
			}
			executor.shutdownNow();
			System.out.println("shutdown finished");
		}

	}
}
