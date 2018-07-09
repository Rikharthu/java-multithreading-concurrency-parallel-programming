package com.example.concurrencybasics.synchronization;

public class Counter {

    public int count = 0;

    public void increment() {
        count = count + 1;
    }

    public int getCount() {
        return count;
    }
}
