package com.example.parallelprogramming;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

// More info:
// http://www.baeldung.com/java-fork-join
/*
Fork/Join Framework provides tools to help speed up parallel processing by attempting to
use all available processor cores – which is accomplished through a divide and conquer approach.

In practice, this means that the framework first “forks”, recursively breaking the task into smaller
independent subtasks until they are simple enough to be executed asynchronously.

After that, the “join” part begins, in which results of all subtasks are recursively joined into
a single result, or in the case of a task which returns void, the program simply waits until every
subtask is executed.

To provide effective parallel execution, the fork/join framework uses a pool of threads called the
ForkJoinPool, which manages worker threads of type ForkJoinWorkerThread.
 */

public class Main {

    public static void main(String[] args) {
        int[] array = new int[500_000_000];
        Random rnd = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = 5;
        }

        AsyncSum aSum = new AsyncSum(array, 0, array.length - 1);

//        ForkJoinPool pool = new ForkJoinPool(2);
        ForkJoinPool pool = ForkJoinPool.commonPool();
        System.out.println("Starting parallel");
        long t1 = System.currentTimeMillis();
        // invoke() method forks the task and waits for the result.
        // Doesn't need manual join
        pool.invoke(aSum);

        // submit() or execute() just submits a task into the pool without waiting
        // need to manually join() task
//        pool.execute(aSum);
//        aSum.join();
//        int res=aSum.SUM;
        long t2 = System.currentTimeMillis();
        long diff = t2 - t1;
        System.out.println("AsyncSum: " + aSum.SUM + ", time took: " + diff);

        System.out.println("Starting sequential");
        t1 = System.currentTimeMillis();
        double sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += 1.0 / array[i];
        }
        t2 = System.currentTimeMillis();
        diff = t2 - t1;
        System.out.println("Sequential: " + sum + ", time took: " + diff);

        AddCharsTask task = new AddCharsTask('x', 2313);
        String result = pool.invoke(task);
        System.out.println("Result length: " + result.length());
        System.out.println(result.substring(0, 10) + "...");
    }
}

// RecursiveAction is a ForkJoinTask that doesn't return a vale
class AsyncSum extends RecursiveAction {
    private static int sIteration = 0;

    int[] A; // input array
    int LO, HI; // subrange
    double SUM; // return value

    public static final int TRESHOLD = 1000;

    public AsyncSum(int[] a, int LO, int HI) {
        A = a;
        this.LO = LO;
        this.HI = HI;
    }

    @Override
    protected void compute() {
        if (HI - LO < TRESHOLD) {
            // Compute sequentially
            for (int i = LO; i <= HI; i++) SUM += 1.0 / A[i];
        } else {
            // Fork part: split intensive job into multiple sub tasks
            int mid = (LO + HI) / 2;
            AsyncSum l = new AsyncSum(A, LO, mid);
            AsyncSum r = new AsyncSum(A, mid + 1, HI);

            // Join part:
            // Recursively join subtasks together
            invokeAll(l, r);
            // Same as:
//            l.fork();
//            r.fork();
//            l.join();
//            r.join();

            // Other option:
            // Compute left part in parallel
            // l.compute();
            // And compute right part synchronously
            // r.compute();

            SUM += l.SUM + r.SUM;
        }
    }
}

class AddCharsTask extends RecursiveTask<String> {

    private StringBuilder sb;
    private char character;
    private int count;

    public AddCharsTask(char character, int count) {
        sb = new StringBuilder();
        this.character = character;
        this.count = count;
    }

    @Override
    protected String compute() {
        if (count > 100) {
            // Split work
            int l = count / 2;
            int r = count - l;

            AddCharsTask task1 = new AddCharsTask(character, l);
            AddCharsTask task2 = new AddCharsTask(character, r);

            // Launch both tasks
            task1.fork();
            task2.fork();

            // Join and extract results
            sb.append(task1.join());
            sb.append(task2.join());
        } else {
            // Execute synchronously
            for (int i = 0; i < count; i++) {
                sb.append(character);
            }
        }
        return sb.toString();
    }
}
