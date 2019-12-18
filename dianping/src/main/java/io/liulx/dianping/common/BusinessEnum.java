package io.liulx.dianping.common;

/**
 * 异常枚举
 *
 * @since 2019-12-19
 */
public enum BusinessEnum {
  UNKNOWN_ERROR(10000, "Unknown Error"),
  NO_HANLDER_FOUND(10001, "Can't find the path"),
  BIND_EXCEPTION(10002, "The request is invalid"),
  NO_OBJECT_FOUND(10003, "Not found");

  private Integer code;

  private String message;

  BusinessEnum(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  public Integer getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
