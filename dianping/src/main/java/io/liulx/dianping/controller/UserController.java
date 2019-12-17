package io.liulx.dianping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户
 *
 * @since 2019-12-17
 */
@Controller("/user")
@RequestMapping("/user")
public class UserController {

  @RequestMapping("/test")
  @ResponseBody
  public String test() {
    return "test";
  }
}
