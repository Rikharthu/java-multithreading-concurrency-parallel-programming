package com.example.concurrencybasics.synchronization;


/*

Synchronization

Thread interference and memory consistency errors can be avoided
by ensuring the following two things:

Only one thread can read and write a shared variable at a time.
When one thread is accessing a shared variable, other threads
should wait until the first thread is done. This guarantees that
the access to a shared variable is Atomic, and multiple threads
do not interfere.

Whenever any thread modifies a shared variable, it automatically
establishes a happens-before relationship with subsequent reads
and writes of the shared variable by other threads. This guarantees
that changes done by one thread are visible to others.

Luckily, Java has a synchronized keyword using which you can synchronize
access to any shared resource, thereby avoiding both kinds of errors.

In case of static methods, synchronization is associated with the Class object.
*/
public class SynchronizedCounter {

    private int count = 0;

    // Synchronized Method
    public synchronized void increment() {
        count = count + 1;
    }

    // Or using synchronized block with lock object
//    public void increment() {
//        // Synchronized Block -
//
//        // Acquire Lock
//        synchronized (this) {
//            count = count + 1;
//        }
//        // Release Lock
//    }

    public int getCount() {
        return count;
    }
}
