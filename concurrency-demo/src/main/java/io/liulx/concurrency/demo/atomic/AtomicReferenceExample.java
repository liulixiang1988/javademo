package io.liulx.concurrency.demo.atomic;

import io.liulx.concurrency.demo.annotation.ThreadSafe;
import java.util.concurrent.atomic.AtomicReference;
import lombok.extern.slf4j.Slf4j;

/**
 * AtomicReferenceExample
 *
 * @since 2020-11-22
 */
@ThreadSafe
@Slf4j
public class AtomicReferenceExample {
  private static AtomicReference<Integer> count = new AtomicReference<>(0);

  public static void main(String[] args) {
    count.compareAndSet(0, 2);
    count.compareAndSet(0, 1);
    count.compareAndSet(1, 3);
    count.compareAndSet(2, 4);
    count.compareAndSet(3, 5);
    log.info("count:{}", count.get());
  }
}
