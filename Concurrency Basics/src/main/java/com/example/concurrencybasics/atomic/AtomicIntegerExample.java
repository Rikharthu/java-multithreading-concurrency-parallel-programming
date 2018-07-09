package com.example.concurrencybasics.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AtomicIntegerExample {

    /*
    The AtomicInteger.incrementAndGet() method is atomic, so you can safely call it from several threads simultaneously
    and be sure that the access to the count variable will be synchronized.
     */
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        AtomicCounter atomicCounter = new AtomicCounter();

        for (int i = 0; i < 1000; i++) {
            executorService.submit(() -> atomicCounter.incrementAndGet());
        }

        executorService.shutdown();
        executorService.awaitTermination(60, TimeUnit.SECONDS);

        System.out.println("Final Count is : " + atomicCounter.getCount());
    }
}
