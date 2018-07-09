package com.example.concurrencybasics.locks;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
ReadWriteLock consists of a pair of locks - one for read access and one for write access.
The read lock may be held by multiple threads simultaneously as long as the write lock is not held by any thread.

ReadWriteLock allows for an increased level of concurrency.
It performs better compared to other locks in applications where there are
fewer writes than reads.
 */
public class ReadWriteCounter {

    /*
    Multiple threads can execute the getCount() method as long as no thread calls incrementAndGetCount().
    If any thread calls incrementAndGetCount() method and acquires the write-lock, then all the reader
    threads will pause their execution and wait for the writer thread to return.
     */

    ReadWriteLock lock = new ReentrantReadWriteLock();

    private int count = 0;

    public int incrementAndGetCount() {
        lock.writeLock().lock();
        try {
            count = count + 1;
            return count;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public int getCount() {
        lock.readLock().lock();
        try {
            return count;
        } finally {
            lock.readLock().unlock();
        }
    }
}
