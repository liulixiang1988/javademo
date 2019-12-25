package io.liulx.dianping.request;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * Login Request
 *
 * @since 2019-12-25
 */
@Data
public class LoginReq {

  @NotEmpty(message = "telephone can't be empty")
  private String telephone;
  @NotEmpty(message = "password can't be empty")
  private String password;
}
