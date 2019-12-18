package io.liulx.dianping.common;

import lombok.Builder;
import lombok.Data;

/**
 * 通用响应
 *
 * @since 2019-12-18
 */
@Data
public class CommonDataResp<T> extends CommonResp {

  @Builder
  public CommonDataResp(int code, String message, T data) {
    super(code, message);
    this.data = data;
  }

  /**
   * 结果数据
   */
  private T data;

  public static <T> CommonDataResp<T> create(int code, String message, T data) {
    return CommonDataResp.<T>builder().code(code).message(message).data(data).build();
  }

  public static <T> CommonDataResp<T> create(T data) {
    return create(0, "ok", data);
  }
}
