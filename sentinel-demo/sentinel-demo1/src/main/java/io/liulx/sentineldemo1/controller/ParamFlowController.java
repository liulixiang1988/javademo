package io.liulx.sentineldemo1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ParamFlow Controller
 *
 * @since 2021-10-29
 */
@RestController
@RequestMapping(value="/param-flow")
public class ParamFlowController {

  @GetMapping(path="")
  public String get(@RequestParam("id") String id) {
    return "hello, " + id;
  }

}
