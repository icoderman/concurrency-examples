Source : http://winterbe.com/posts/2015/04/07/java8-concurrency-tutorial-thread-executor-examples/

Locks

The java.util.concurrent.locks package provides:
- The ability to duplicate traditional synchronized blocks.
- Non-block scoped locking—obtain a lock in one method and release it in another (this can be dangerous, though).
- Multiple wait/notify/notifyAll pools per lock—threads can select which pool (Condition) they wait on.
- The ability to attempt to acquire a lock and take an alternative action if locking fails.
- An implementation of a multiple-reader, single-writer lock.