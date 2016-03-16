package locks;

import synchronization.ConcurrentUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;

/*
Java 8 ships with a new kind of lock called StampedLock which also support read and write locks just like in the example
above. In contrast to ReadWriteLock the locking methods of a StampedLock return a stamp represented by a long value.
You can use these stamps to either release a lock or to check if the lock is still valid. Additionally stamped locks
support another lock mode called optimistic locking.
 */
public class StampedLockDemo {
	public static void main(String[] args) {

		ExecutorService executor = Executors.newFixedThreadPool(2);
		Map<String, String> map = new HashMap<>();
		StampedLock lock = new StampedLock();

		executor.submit(() -> {
			long stamp = lock.writeLock();
			try {
				System.out.println("I'm writing to map");
				ConcurrentUtils.sleep(1);
				map.put("foo", "bar");
			} finally {
				lock.unlockWrite(stamp);
			}
		});

		Runnable readTask = () -> {
			long stamp = lock.readLock();
			try {
				System.out.println(map.get("foo"));
				ConcurrentUtils.sleep(1);
			} finally {
				lock.unlockRead(stamp);
			}
		};
		executor.submit(readTask);
		executor.submit(readTask);

		ConcurrentUtils.stop(executor);
	}
}
