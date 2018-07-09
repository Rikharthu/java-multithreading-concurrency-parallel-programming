package com.example.concurrencybasics.callable;

import java.util.concurrent.*;

public class FutureAndCallableExample {

    /*
    Callable is a task that returns a result
     */
    public static void main(String[] args) throws InterruptedException {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                // Callable can throw a checked Exception => no need to try/catch
                Thread.sleep(2000);
                return "Hello. I was executed on thread '" + Thread.currentThread().getName() + "'";
            }
        };

        // Manually execute callable on the same thread
        System.out.println("Executing callable on the same thread");
        try {
            String result = callable.call();
            System.out.println("Callable result: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Callables can be submitted to the executor service for execution
        // submit() method in this case returns a Future object
        // Future represents the result of a computation that will be completed at a later opint of time in future
        ExecutorService service = Executors.newSingleThreadExecutor();
        System.out.println("Submitting callable");
        Future<String> future = service.submit(callable);

        System.out.println("Do something else while callable is getting executed");

        System.out.println("Retreive the result of the Future");
        // Future.get() blocks until the result is available
        try {
            String result = future.get();
            System.out.println("Future result: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // You can also manually check the future status with isDone() method
        System.out.println("Submitting callable again");
        future = service.submit(callable);
        while (!future.isDone()) {
            System.out.println("Task is still not done");
            Thread.sleep(200);
        }
        System.out.println("Task completed. Retrieving result.");
        try {
            String result = future.get();
            System.out.println("Future result: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // Future computation can be canceled
        long startTime = System.nanoTime();
        future = service.submit(callable);
        while (!future.isDone()) {
            System.out.println("Task is still not done...");
            Thread.sleep(200);
            double elapsedTimeInSec = (System.nanoTime() - startTime) / 1000000000.0;

            if (elapsedTimeInSec > 1) {
                future.cancel(true);
            }
        }
        System.out.println("Task completed. Retrieving result.");
        if (!future.isCancelled()) {
            try {
                String result = future.get();
                System.out.println("Future result: " + result);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Can't retrieve result from the future. It has been cancelled");
        }

        // You can also specify timeout to Future.get() method:
        // future.get(1, TimeUnit.SECONDS)
        // This will throw TimeoutException if task is not completed within the specified time

        service.shutdown();
    }
}
