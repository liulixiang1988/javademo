package io.liulx.dianping;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"io.liulx.dianping"})
@MapperScan("io.liulx.dianping.dal")
public class DianpingApplication {

  public static void main(String[] args) {
    SpringApplication.run(DianpingApplication.class, args);
  }
}
