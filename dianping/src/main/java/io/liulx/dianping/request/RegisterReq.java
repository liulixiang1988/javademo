package io.liulx.dianping.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * 用户名
 *
 * @since 2019-12-25
 */
@Data
public class RegisterReq {

  @NotBlank(message = "Telephone cann't be empty")
  private String telephone;

  @NotBlank(message = "password can't be empty")
  private String password;

  @NotBlank(message = "nickname can't be empty")
  private String nickName;

  @NotNull(message = "gender can't be null")
  private Integer gender;
}
