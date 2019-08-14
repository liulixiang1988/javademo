package io.liulx.concurrency;

public class NotificationDemo {
    private volatile boolean go = false;

    public static void main(String[] args) throws InterruptedException {
        final NotificationDemo test = new NotificationDemo();

        Runnable waitTask = () -> {
            try {
                test.shouldGo();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " finishede execution");
        };

        Runnable notifyTask = () -> {
            test.go();
            System.out.println(Thread.currentThread().getName() + " finished execution");
        };

        Thread t1 = new Thread(waitTask, "WT1");
        Thread t2 = new Thread(waitTask, "WT2");
        Thread t3 = new Thread(waitTask, "WT3");
        Thread t4 = new Thread(notifyTask, "NT1");

        t1.start();
        t2.start();
        t3.start();

        Thread.sleep(200);

        t4.start();
    }

    private synchronized void shouldGo() throws InterruptedException {
        while (!go) {
            System.out.println(Thread.currentThread() + " is going to wait on this thread");
            wait();
            System.out.println(Thread.currentThread() + " is woken up");
        }
        go = false;
    }

    private synchronized void go() {
        while (!go) {
            System.out.println(Thread.currentThread() + " is going to notify all or one thread");
            go = true;
            notifyAll();
        }
    }
}
