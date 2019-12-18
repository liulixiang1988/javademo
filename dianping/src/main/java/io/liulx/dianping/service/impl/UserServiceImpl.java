package io.liulx.dianping.service.impl;

import io.liulx.dianping.dal.UserModelMapper;
import io.liulx.dianping.model.UserModel;
import io.liulx.dianping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User Service Implement
 *
 * @since 2019-12-17
 */
@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserModelMapper userModelMapper;

  @Override
  public UserModel getUser(Integer id) {
    return userModelMapper.selectByPrimaryKey(id);
  }
}
