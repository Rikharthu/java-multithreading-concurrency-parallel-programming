package com.example.concurrencybasics.completablefutures;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/*
In Java 8, the CompletableFuture class was introduced.
Along with the Future interface, it also implemented the CompletionStage interface.
This interface defines the contract for an asynchronous computation step that can be combined with other steps.

CompletableFuture is at the same time a building block and a framework with about 50 different methods for composing,
combining, executing asynchronous computation steps and handling errors.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        new Main().execute();
    }

    private void execute() throws InterruptedException, ExecutionException {
        Future<String> completableFuture = calculateAsync();
        // ...
        String result = completableFuture.get();
        System.out.println("Result: " + result);

        Future<String> completedFuture = CompletableFuture.completedFuture("Hello, World!");
        System.out.println("Result of completed future: " + completedFuture.get());

        Future<String> future = calculateAsyncWithCancellation();
        // future.get(); // CancellationException

        // Encapsulated Computation Logic
        // You can use Runnable and Supplier types to provide CompletableFuture logic
        CompletableFuture<String> suppliedFuture = CompletableFuture.supplyAsync(() -> "Hello");
        System.out.println("Result of suppliedFuture: " + suppliedFuture.get());
        CompletableFuture<Void> runnableFuture = CompletableFuture.runAsync(() -> {
            System.out.println("Running inside future");
        });
    }


    /**
     * CompletableFuture can be used as Future with additional completion logic
     */
    public Future<String> calculateAsync() throws InterruptedException {
        CompletableFuture<String> completableFuture =
                new CompletableFuture<>();
        Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(500);
            completableFuture.complete("Hello");
            return null;
        });

        return completableFuture;
    }

    public Future<String> calculateAsyncWithCancellation() throws InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(500);
            completableFuture.cancel(false);
            return null;
        });

        return completableFuture;
    }
}
