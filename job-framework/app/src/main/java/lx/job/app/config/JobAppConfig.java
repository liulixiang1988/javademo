package lx.job.app.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 配置
 * Created by liulixiang on 16/8/26.
 */
@Configuration
@ComponentScan(basePackages = "lx.job.app")
@Import(lx.job.base.config.BaseConfig.class)
public class JobAppConfig {
}
