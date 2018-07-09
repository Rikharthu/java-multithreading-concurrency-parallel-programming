package com.example.concurrencybasics.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/*
Atomic classes internally use compare-and-swap instructions supported by modern CPUs
to achieve synchronization.
These instructions are generally much faster than locks.
 */
public class AtomicCounter {
    private AtomicInteger count = new AtomicInteger(0);

    public int incrementAndGet() {
        return count.incrementAndGet();
    }

    public int getCount() {
        return count.get();
    }
}
