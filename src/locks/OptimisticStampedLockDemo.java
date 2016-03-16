package locks;

import utils.ConcurrentUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;

/*
An optimistic read lock is acquired by calling tryOptimisticRead() which always returns a stamp without blocking the
current thread, no matter if the lock is actually available. If there's already a write lock active the returned stamp
equals zero. You can always check if a stamp is valid by calling lock.validate(stamp)
 */
public class OptimisticStampedLockDemo {

	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(2);

		StampedLock lock = new StampedLock();

		executor.submit(() -> {
			long stamp = lock.tryOptimisticRead();
			try {
				System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
				ConcurrentUtils.sleep(1);
				System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
				ConcurrentUtils.sleep(2);
				System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
			} finally {
				lock.unlock(stamp);
			}
		});

		executor.submit(() -> {
			long stamp = lock.writeLock();
			try {
				System.out.println("Write Lock acquired");
				ConcurrentUtils.sleep(2);
			} finally {
				lock.unlock(stamp);
				System.out.println("Write done");
			}
		});

		ConcurrentUtils.stop(executor);
	}
}
