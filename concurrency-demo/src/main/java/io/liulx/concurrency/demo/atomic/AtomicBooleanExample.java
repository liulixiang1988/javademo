package io.liulx.concurrency.demo.atomic;

import io.liulx.concurrency.demo.annotation.ThreadSafe;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;

/**
 * TODO Class Description
 *
 * @since 2020-11-22
 */
@Slf4j
@ThreadSafe
public class AtomicBooleanExample {

  public static int clientTotal = 5000;
  public static int threadTotal = 200;

  public static AtomicBoolean count = new AtomicBoolean(false);

  public static void main(String[] args) throws InterruptedException {
    ExecutorService executorService = Executors.newCachedThreadPool();
    final Semaphore semaphore = new Semaphore(threadTotal);
    final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
    for (int i = 0; i < clientTotal; i++) {
      executorService.execute(() -> {
        try {
          semaphore.acquire();
          if (count.compareAndSet(false, true)) {
            log.info("set count to true");
          }
          semaphore.release();
        } catch (InterruptedException e) {
          log.error("ex:", e);
        }
        countDownLatch.countDown();
      });
    }
    countDownLatch.await();
    executorService.shutdown();
    log.info("count: {}", count.get());
  }
}
