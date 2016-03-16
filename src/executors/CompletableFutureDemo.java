package executors;

import utils.ConcurrentUtils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*

CompletableFuture provides a mechanism for aynchronous reaction upon result completion.

Static factory methods in CompletableFuture:
<U> CompletableFuture<U> supplyAsync(Supplier<U> supplier)
<U> CompletableFuture<U> supplyAsync(Supplier<U> supplier, Executor executor)
<U> CompletableFuture<U> runAsync(Runnable runnable)
<U> CompletableFuture<U> runAsync(Runnable runnable, Executor executor)

*/

public class CompletableFutureDemo {

	public static void main(String[] args) {

		ExecutorService executor = Executors.newFixedThreadPool(1);
		CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
			try {
				System.out.println("Supplier starts running...");
				TimeUnit.SECONDS.sleep(5);
				return 123;
			} catch (InterruptedException e) {
				throw new IllegalStateException("task interrupted", e);
			}
		}, executor);

		future.thenAccept(System.out::println);
		System.out.println("Main thread isn't blocking");
		ConcurrentUtils.stop(executor);
	}

}
