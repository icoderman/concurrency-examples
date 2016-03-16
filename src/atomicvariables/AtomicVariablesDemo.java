package atomicvariables;

public class AtomicVariablesDemo {

	public static void main(String[] args) throws InterruptedException {

		Counter unsafeCounter = new UnsafeCounter(); // the shared object
		IncrementerThread it1 = new IncrementerThread(unsafeCounter);
		IncrementerThread it2 = new IncrementerThread(unsafeCounter);
		it1.start(); // thread 1 increments the count by 10000
		it2.start(); // thread 2 increments the count by 10000
		it1.join(); // wait for thread 1 to finish
		it2.join(); // wait for thread 2 to finish
		System.out.println("Unsafe Counter: " + unsafeCounter.getValue()); // rarely 20000 // lowest 11972

		Counter threadSafeCounter = new ThreadSafeCounter(); // the shared object
		IncrementerThread it3 = new IncrementerThread(threadSafeCounter);
		IncrementerThread it4 = new IncrementerThread(threadSafeCounter);
		it3.start(); // thread 1 increments the count by 10000
		it4.start(); // thread 2 increments the count by 10000
		it3.join(); // wait for thread 3 to finish
		it4.join(); // wait for thread 4 to finish
		System.out.println("Thread Safe Counter: " + threadSafeCounter.getValue()); // always 20000
	}

}
