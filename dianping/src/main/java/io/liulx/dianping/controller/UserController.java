package io.liulx.dianping.controller;

import io.liulx.dianping.common.BusinessEnum;
import io.liulx.dianping.common.BusinessException;
import io.liulx.dianping.common.CommonDataResp;
import io.liulx.dianping.common.CommonResp;
import io.liulx.dianping.model.UserModel;
import io.liulx.dianping.request.LoginReq;
import io.liulx.dianping.request.RegisterReq;
import io.liulx.dianping.response.UserResp;
import io.liulx.dianping.service.UserService;
import io.liulx.dianping.util.CommonUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 用户
 *
 * @since 2019-12-17
 */
@Controller("/user")
@RequestMapping("/user")
public class UserController {

  public static final String CURRENT_USER_SESSION = "currentUserSession";

  @Autowired
  private UserService userService;

  @Autowired
  private HttpServletRequest httpServletRequest;

  @RequestMapping("/test")
  @ResponseBody
  public String test() {
    return "test";
  }

  @RequestMapping("/index")
  public ModelAndView index() {
    String userName = "liulixiang";
    ModelAndView modelAndView = new ModelAndView("/index.html");
    modelAndView.addObject("username", userName);
    return modelAndView;
  }

  @RequestMapping("/register")
  @ResponseBody
  public CommonDataResp<UserResp> register(@Valid @RequestBody RegisterReq registerReq,
      BindingResult bindingResult) throws BusinessException {
    if (bindingResult.hasErrors()) {
      throw new BusinessException(BusinessEnum.PARAMETER_VALID_ERROR,
          CommonUtil.processErrorString(bindingResult));
    }
    UserModel registerUser = new UserModel();
    registerUser.setTelephone(registerReq.getTelephone());
    registerUser.setPassword(registerReq.getPassword());
    registerUser.setNickName(registerReq.getNickName());
    registerUser.setGender(registerReq.getGender());

    UserModel userModel = userService.register(registerUser);

    UserResp userResp = new UserResp(userModel);
    return CommonDataResp.create(userResp);
  }

  @RequestMapping("/login")
  @ResponseBody
  public CommonDataResp<UserResp> login(@Valid @RequestBody LoginReq loginReq,
      BindingResult bindingResult)
      throws BusinessException {
    if (bindingResult.hasErrors()) {
      throw new BusinessException(BusinessEnum.PARAMETER_VALID_ERROR,
          CommonUtil.processErrorString(bindingResult));
    }
    HttpSession session = httpServletRequest.getSession();
    if (session != null) {
      session.invalidate();
    }
    UserModel userModel = userService.login(loginReq.getTelephone(), loginReq.getPassword());
    httpServletRequest.getSession(true).setAttribute(CURRENT_USER_SESSION, userModel);
    UserResp userResp = new UserResp(userModel);
    return CommonDataResp.create(userResp);
  }

  @RequestMapping("/logout")
  @ResponseBody
  public CommonResp logout() {
    httpServletRequest.getSession().invalidate();
    return CommonResp.commonBuilder().code(0).message("logout").build();
  }

  @RequestMapping("/getcurrentuser")
  public @ResponseBody
  CommonResp getCurrentUser() {
    UserModel userModel = (UserModel) httpServletRequest.getSession()
        .getAttribute(CURRENT_USER_SESSION);
    if (userModel == null) {
      return new CommonResp(BusinessEnum.LOGIN_FAIL);
    }
    UserResp userResp = new UserResp(userModel);
    return CommonDataResp.create(userResp);
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
