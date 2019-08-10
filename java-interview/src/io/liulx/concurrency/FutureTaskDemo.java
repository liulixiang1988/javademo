package io.liulx.concurrency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> task = new FutureTask<>(new MyCallable());
        new Thread(task).start();
        if (!task.isDone()) {
            System.out.println("task is not done, please wait!");
        }
        System.out.println("Result is " + task.get());
    }
}
