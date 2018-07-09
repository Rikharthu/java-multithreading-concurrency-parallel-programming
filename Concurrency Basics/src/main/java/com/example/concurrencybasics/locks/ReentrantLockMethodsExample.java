package com.example.concurrencybasics.locks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReentrantLockMethodsExample {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        ReentrantLockMethodsCounter lockMethodsCounter = new ReentrantLockMethodsCounter();

        executorService.submit(() -> {
            System.out.println("IncrementCount (First Thread) : " +
                    lockMethodsCounter.incrementAndGet() + "\n");
        });

        executorService.submit(() -> {
            System.out.println("IncrementCount (Second Thread) : " +
                    lockMethodsCounter.incrementAndGet() + "\n");
        });

        executorService.shutdown();
    }
}
