package com.example.concurrencybasics.callable;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class InvokeAllExample {

    /*
    You can execute multiple tasks by passing a collection of Callables to the invokeAll() method.
    The invokeAll() returns a list of Futures. Any call to future.get() will block until all the
    Futures are complete.
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        Callable<String> task1 = () -> {
            Thread.sleep(2000);
            return "Result of Task1";
        };

        Callable<String> task2 = () -> {
            Thread.sleep(1000);
            return "Result of Task2";
        };

        Callable<String> task3 = () -> {
            Thread.sleep(5000);
            return "Result of Task3";
        };

        List<Callable<String>> taskList = Arrays.asList(task1, task2, task3);

        List<Future<String>> futures = executorService.invokeAll(taskList);

        for (Future<String> future : futures) {
            // The result is printed only after all the futures are complete. (i.e. after 5 seconds)
            System.out.println(future.get());
        }

        executorService.shutdown();
    }
}
