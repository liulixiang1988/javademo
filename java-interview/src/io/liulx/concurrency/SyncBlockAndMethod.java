package io.liulx.concurrency;

public class SyncBlockAndMethod {
    public void syncsTask() {
        synchronized (this) {
            System.out.println("hello");
            synchronized (this) {
                System.out.println("world");
            }
        }
    }

    public synchronized void syncTask(){
        System.out.println("hello,world!");
    }
}
