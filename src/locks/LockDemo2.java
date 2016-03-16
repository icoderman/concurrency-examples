package locks;

import synchronization.ConcurrentUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo2 {

	public static void main(String[] args) {

		ExecutorService executor = Executors.newFixedThreadPool(2);
		ReentrantLock lock = new ReentrantLock();

		executor.submit(() -> {
			lock.lock();
			try {
				ConcurrentUtils.sleep(1);
			} finally {
				lock.unlock();
			}
		});

		executor.submit(() -> {
			System.out.println("Locked: " + lock.isLocked());
			System.out.println("Held by me: " + lock.isHeldByCurrentThread());
			/*
			The method tryLock() as an alternative to lock() tries to acquire the lock without pausing the current thread.
			The boolean result must be used to check if the lock has actually been acquired before accessing any shared mutable variables.
			 */
			boolean locked = lock.tryLock();
			System.out.println("Lock acquired: " + locked);
		});

		ConcurrentUtils.stop(executor);
	}
}
