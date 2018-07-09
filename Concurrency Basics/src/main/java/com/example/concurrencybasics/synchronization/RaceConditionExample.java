package com.example.concurrencybasics.synchronization;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RaceConditionExample {

    /*
    When multiple threads try to read and write a shared variable concurrently,
    and these read and write operations overlap in execution, then the final
    outcome depends on the order in which the reads and writes take place, which
    is unpredictable. This phenomenon is called Race condition.

    ThreadA : Retrieve count, initial value = 0
    ThreadB : Retrieve count, initial value = 0
    ThreadA : Increment retrieved value, result = 1
    ThreadB : Increment retrieved value, result = 1
    ThreadA : Store the incremented value, count is now 1
    ThreadB : Store the incremented value, count is now 1

    Both the threads try to increment the count by one, but the final result is 1 instead of 2
    because the operations executed by the threads interleave with each other.
    In the above case, the update done by ThreadA is lost.
     */

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Counter counter = new Counter();

        for (int i = 0; i < 1000; i++) {
            executorService.submit(() -> counter.increment());
        }

        executorService.shutdown();
        executorService.awaitTermination(60, TimeUnit.SECONDS);

        System.out.println("Final count is : " + counter.getCount());
    }
}
