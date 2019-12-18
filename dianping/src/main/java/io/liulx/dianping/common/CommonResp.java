package io.liulx.dianping.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 通用错误
 *
 * @since 2019-12-19
 */
@Getter
@Setter
@AllArgsConstructor
@Builder(builderMethodName = "commonBuilder")
public class CommonResp {

  /**
   * 响应码
   */
  private int code;

  /**
   * 消息
   */
  private String message;

  public CommonResp(BusinessEnum businessEnum) {
    this.code = businessEnum.getCode();
    this.message = businessEnum.getMessage();
  }
}
