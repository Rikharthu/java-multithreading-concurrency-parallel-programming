package com.example.concurrencybasics.completablefutures;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ErrorHandling {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String name = null;

        // ...

        CompletableFuture<String> completableFuture
                = CompletableFuture.supplyAsync(() -> {
            if (name == null) {
                throw new RuntimeException("Computation error!");
            }
            return "Hello, " + name;
        }).handle((value, error) -> "Hello, Stranger"); // Error handling

        System.out.println(completableFuture.get());
    }
}
