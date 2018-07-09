package com.example.concurrencybasics.completablefutures;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ProcessingResultsExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> wordFuture =
                CompletableFuture.supplyAsync(() -> "World");
        CompletableFuture<String> greetingFuture = wordFuture
                .thenApply(w -> "Hello, " + w + "!");

        System.out.println(greetingFuture.get());

        CompletableFuture<Void> printFuture = CompletableFuture
                .supplyAsync(() -> "John")
                .thenApply(w -> "Hello, " + w + "!")
                .thenAccept(System.out::println)
                .thenRun(() -> System.out.println("Finished greeting"));
    }
}
