package io.liulx.dianping.controller;

import io.liulx.dianping.common.BusinessEnum;
import io.liulx.dianping.common.BusinessException;
import io.liulx.dianping.common.CommonDataResp;
import io.liulx.dianping.model.UserModel;
import io.liulx.dianping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户
 *
 * @since 2019-12-17
 */
@Controller("/user")
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  @RequestMapping("/test")
  @ResponseBody
  public String test() {
    return "test";
  }

  @RequestMapping("/get")
  @ResponseBody
  public CommonDataResp<UserModel> getUser(@RequestParam(name = "id") Integer id)
      throws BusinessException {
    UserModel userModel = userService.getUser(id);
    if (userModel == null) {
      throw new BusinessException(BusinessEnum.NO_OBJECT_FOUND);
    }
    return CommonDataResp.create(userModel);
  }
}
