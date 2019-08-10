package io.liulx.concurrency;

import java.util.concurrent.Callable;

public class MyCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        String value = "test";
        System.out.println("Read to work!");
        Thread.sleep(5000);
        System.out.println("task is done");
        return value;
    }
}
