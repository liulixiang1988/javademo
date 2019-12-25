package io.liulx.dianping.service;

import io.liulx.dianping.common.BusinessException;
import io.liulx.dianping.model.UserModel;

public interface UserService {

  UserModel getUser(Integer id);

  UserModel register(UserModel registerUser) throws BusinessException;

  UserModel login(String telephone, String password) throws BusinessException;
}
