package com.example.concurrencybasics.synchronization;

public class MemoryInconsistencyErrorExample {
    private static boolean sayHello = false;

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() -> {
            while (!sayHello) {
            }

            System.out.println("Hello World!");

            while (sayHello) {
            }

            System.out.println("Good Bye!");
        });

        thread.start();

        Thread.sleep(1000);
        System.out.println("Say Hello..");
        sayHello = true;

        Thread.sleep(1000);
        System.out.println("Say Bye..");
        sayHello = false;

        /*
        # Ideal Output
        Say Hello..
        Hello World!
        Say Bye..
        Good Bye!

        # Actual Output
        Say Hello..
        Say Bye..

        That is what Memory Consistency Error is.
        The first thread is unaware of the changes done by the main thread to the sayHello variable.

        using 'volatile' keyword will fix this error
         */
    }
}
