package atomicvariables;

import java.util.concurrent.atomic.AtomicInteger;

class ThreadSafeCounter implements Counter {

	private AtomicInteger count = new AtomicInteger();

	public void increment() {
		count.getAndIncrement(); // atomic operation
	}

	public int getValue() {
		return count.intValue();
	}
}