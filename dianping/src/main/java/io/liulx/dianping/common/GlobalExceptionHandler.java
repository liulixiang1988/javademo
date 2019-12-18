package io.liulx.dianping.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 全局异常捕获
 *
 * @since 2019-12-19
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  @ResponseBody
  public CommonResp handlerException(HttpServletRequest request, HttpServletResponse response,
      Exception ex) {
    if (ex instanceof BusinessException) {
      return ((BusinessException) ex).getError();
    } else if (ex instanceof NoHandlerFoundException) {
      return new CommonResp(BusinessEnum.NO_HANLDER_FOUND);
    } else if (ex instanceof ServletRequestBindingException) {
      return new CommonResp(BusinessEnum.BIND_EXCEPTION);
    } else {
      return new CommonResp(BusinessEnum.UNKNOWN_ERROR);
    }
  }
}
