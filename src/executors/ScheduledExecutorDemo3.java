package executors;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorDemo3 {

	public static void main(String[] args) {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

		Runnable task = () -> {
			try {
				TimeUnit.SECONDS.sleep(2);
				System.out.println("Scheduling: " + System.nanoTime());
			}
			catch (InterruptedException e) {
				System.err.println("task interrupted");
			}
		};

		/*
		The difference between scheduleAtFixedRate is that the wait time period applies between the end of a task and
		the start of the next task.
		 */
		executor.scheduleWithFixedDelay(task, 0, 1, TimeUnit.SECONDS);
	}

}
