package io.liulx.concurrency.demo.atomic;

import io.liulx.concurrency.demo.annotation.ThreadSafe;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * AtomicIntegerFieldUpdaterExample
 *
 * @since 2020-11-22
 */
@Slf4j
@ThreadSafe
public class AtomicIntegerFieldUpdaterExample {

  @Getter
  @Setter
  private volatile int count = 100;

  public static void main(String[] args) {
    AtomicIntegerFieldUpdater<AtomicIntegerFieldUpdaterExample> updater =
        AtomicIntegerFieldUpdater.newUpdater(AtomicIntegerFieldUpdaterExample.class, "count");
    AtomicIntegerFieldUpdaterExample example = new AtomicIntegerFieldUpdaterExample();
    if (updater.compareAndSet(example, 100, 120)) {
      log.info("update 1 ok. {}", example.getCount());
    }
    if (updater.compareAndSet(example, 100, 120)) {
      log.info("update 2 ok. {}", example.getCount());
    } else {
      log.info("update 2 failed. {}", example.getCount());
    }
  }
}
