package io.liulx.concurrency.demo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * Concurrency Test
 *
 * @since 2020-11-22
 */
@Slf4j
public class ConcurrencyTest {
  public static int clientTotal = 5000;
  public static int threadTotal = 200;

  public static int count = 0;

  @Test
  public void test() throws InterruptedException {
    ExecutorService executorService = Executors.newCachedThreadPool();
    final Semaphore semaphore = new Semaphore(threadTotal);
    final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
    for (int i = 0; i < clientTotal; i++) {
      executorService.execute(()->{
        try{
          semaphore.acquire();
          count++;
          semaphore.release();
        } catch (InterruptedException e) {
          log.error("ex:", e);
        }
        countDownLatch.countDown();
      });
    }
    countDownLatch.await();
    executorService.shutdown();
    log.info("count: {}", count);
  }
}
