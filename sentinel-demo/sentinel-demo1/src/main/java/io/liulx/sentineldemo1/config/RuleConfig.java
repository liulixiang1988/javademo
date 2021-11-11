package io.liulx.sentineldemo1.config;

import com.alibaba.csp.sentinel.slots.block.Rule;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowItem;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

/**
 * Rule Config
 *
 * @since 2021-11-11
 */
@Configuration
@Slf4j
public class RuleConfig {

  @PostConstruct
  public void init() {
    loadFlowRules();
    loadParamFlowRules();
    log.info("RuleConfig init");
  }

  private void loadFlowRules() {
    List<FlowRule> rules = new ArrayList<>();
    FlowRule rule = new FlowRule();
    rule.setResource("HelloWorld");
    rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
    // Set limit QPS to 20.
    rule.setCount(20);
    rules.add(rule);
    print(rules);
    FlowRuleManager.loadRules(rules);
  }

  private void print(List<? extends Rule> rules) {
    try {
      log.info("Rule: {}", new ObjectMapper().writeValueAsString(rules));
    } catch (Exception e) {
      log.error("", e);
    }
  }

  private void loadParamFlowRules() {
    ParamFlowRule rule = new ParamFlowRule("resourceName")
        .setParamIdx(0)
        .setCount(5);
    // 针对 int 类型的参数 2，单独设置限流 QPS 阈值为 10，而不是全局的阈值 5.
    ParamFlowItem item = new ParamFlowItem().setObject(String.valueOf(2))
        .setClassType(int.class.getName())
        .setCount(10);
    rule.setParamFlowItemList(Collections.singletonList(item));
    List<ParamFlowRule> rules = Collections.singletonList(rule);
    print(rules);
    ParamFlowRuleManager.loadRules(rules);
  }
}