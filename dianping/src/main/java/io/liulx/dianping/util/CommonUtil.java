package io.liulx.dianping.util;

import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;

/**
 * 通用
 *
 * @since 2019-12-25
 */
public class CommonUtil {

  public static String processErrorString(BindingResult bindingResult) {
    if (!bindingResult.hasErrors()) {
      return "";
    }
    return bindingResult.getFieldErrors().stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.joining(","));
  }
}
