package com.example.concurrencybasics.locks;

import java.util.concurrent.locks.ReentrantLock;

/*
ReentrantLock is a mutually exclusive lock with the same behavior
as the intrinsic/implicit lock accessed via the synchronized keyword.

ReentrantLock, as the name suggests, possesses reentrant characteristics.
That means a thread that currently owns the lock can acquire it more than
once without any problem.
 */
public class ReentrantLockCounter {
    private final ReentrantLock lock = new ReentrantLock();

    private int count = 0;

    // Thread Safe Increment
    public void increment() {
        lock.lock();
        try {
            count = count + 1;
        } finally {
            // Make sure to unlock even if exception occurs
            lock.unlock();
        }
    }
}
