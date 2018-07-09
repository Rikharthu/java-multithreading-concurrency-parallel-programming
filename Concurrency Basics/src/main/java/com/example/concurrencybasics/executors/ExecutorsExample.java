package com.example.concurrencybasics.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// More info: https://www.callicoder.com/java-executor-service-and-thread-pool-tutorial/
public class ExecutorsExample {

    public static void main(String[] args) {
        System.out.println("Inside: " + Thread.currentThread().getName());

        System.out.println("Creating Executor Service...");
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        System.out.println("Creating a Runnable...");
        Runnable runnable = () -> {
            System.out.println("Executing runnable");
            System.out.println("Inside: " + Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.out.println("Runnable has been interrupted");
            }
            System.out.println("Finished runnable");
        };

        System.out.println("Submit the task specified by the runnable to the executor service");
        executorService.submit(runnable);

        // stops accepting new task, waits for previously submitted tasks to execute and shutdowns the executor
        // executorService.shutdown();

        // interrupts the running tasks and shuts down executor immediately
        executorService.shutdownNow();
    }
}
