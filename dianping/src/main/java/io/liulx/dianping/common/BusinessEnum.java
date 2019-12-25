package io.liulx.dianping.common;

/**
 * 异常枚举
 *
 * @since 2019-12-19
 */
public enum BusinessEnum {
  // 通用类型10000开始
  UNKNOWN_ERROR(10000, "Unknown Error"),
  NO_HANLDER_FOUND(10001, "Can't find the path"),
  BIND_EXCEPTION(10002, "The request is invalid"),
  NO_OBJECT_FOUND(10003, "Not found"),
  PARAMETER_VALID_ERROR(10003, "Parameter valid error"),

  // 用户类型20000开始
  REGISTER_DUP_FAIL(20001, "User already exists"),
  LOGIN_FAIL(20002, "Login failed");

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
