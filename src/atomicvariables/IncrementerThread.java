package atomicvariables;

public class IncrementerThread extends Thread {

	private Counter counter;

	// all instances are passed the same counter
	public IncrementerThread(Counter counter) {
		this.counter = counter;
	}

	public void run() {
		// "i" is local and thread-safe
		for(int i = 0; i < 10000; i++) {
			counter.increment();
		}
	}
}