package io.liulx.dianping.response;

import io.liulx.dianping.model.UserModel;
import lombok.Data;

/**
 * User返回
 *
 * @since 2019-12-25
 */
@Data
public class UserResp {

  private Integer id;

  private String telephone;

  private String nickName;

  private Integer gender;

  public UserResp() {
  }

  public UserResp(UserModel userModel) {
    id = userModel.getId();
    telephone = userModel.getTelephone();
    nickName = userModel.getNickName();
    gender = userModel.getGender();
  }
}
