package io.liulx.dianping.common;

import lombok.Getter;

/**
 * 业务异常
 *
 * @since 2019-12-18
 */
public class BusinessException extends Exception {

  @Getter
  private final CommonResp error;

  public BusinessException(BusinessEnum errorEnum) {
    super();
    error = new CommonResp(errorEnum);
  }
}
