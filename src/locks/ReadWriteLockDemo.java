package locks;

import synchronization.ConcurrentUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {

	public static void main(String[] args) {

		ExecutorService executor = Executors.newFixedThreadPool(2);
		Map<String, String> map = new HashMap<>();
		ReadWriteLock lock = new ReentrantReadWriteLock();

		executor.submit(() -> {
			lock.writeLock().lock();
			try {
				System.out.println("I'm writing to map");
				ConcurrentUtils.sleep(5);
				map.put("foo", "bar");
			} finally {
				lock.writeLock().unlock();
			}
		});

		Runnable readTask = () -> {
			lock.readLock().lock();
			try {
				System.out.println(map.get("foo"));
				ConcurrentUtils.sleep(1);
			} finally {
				lock.readLock().unlock();
			}
		};
		executor.submit(readTask);
		executor.submit(readTask);

		ConcurrentUtils.stop(executor);
	}
}
