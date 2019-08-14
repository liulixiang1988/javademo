package io.liulx.concurrency;

public class WaitSleepDemo {
    public static void main(String[] args) {
        final Object lock = new Object();

        new Thread(() -> {
            System.out.println("Thread A is waiting to get the lock");
            synchronized (lock) {
                try {
                    System.out.println("Thread A gets the lock");
                    Thread.sleep(100);
                    System.out.println("Thread A do wait method");
                    lock.wait();
                    System.out.println("Thread A is done");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            System.out.println("Thread B is waiting to get the lock");
            synchronized (lock) {
                try {
                    System.out.println("Thread B gets the lock");
                    Thread.sleep(100);
                    System.out.println("Thread B do wait method");
                    lock.notify();
                    System.out.println("Thread B is done");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
