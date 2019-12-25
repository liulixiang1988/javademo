package io.liulx.dianping.service.impl;

import io.liulx.dianping.common.BusinessEnum;
import io.liulx.dianping.common.BusinessException;
import io.liulx.dianping.dal.UserModelMapper;
import io.liulx.dianping.model.UserModel;
import io.liulx.dianping.service.UserService;
import io.liulx.dianping.util.EncryptUtil;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  @Override
  @Transactional
  public UserModel register(UserModel registerUser) throws BusinessException {
    registerUser.setCreatedAt(new Date());
    registerUser.setUpdatedAt(new Date());
    try {
      byte[] bytes = EncryptUtil.getSalt();
      registerUser.setPassword(EncryptUtil.pbkdf2Hash(registerUser.getPassword(), bytes));
      String saltStr = Base64.getEncoder().encodeToString(bytes);
      registerUser.setSalt(saltStr);
      userModelMapper.insertSelective(registerUser);
    } catch (DuplicateKeyException | NoSuchAlgorithmException | InvalidKeySpecException ex) {
      throw new BusinessException(BusinessEnum.REGISTER_DUP_FAIL);
    }
    return getUser(registerUser.getId());
  }

  @Override
  public UserModel login(String telephone, String password) throws BusinessException {
    UserModel userModel = userModelMapper.selectByTelephone(telephone);
    if (userModel == null) {
      throw new BusinessException(BusinessEnum.LOGIN_FAIL);
    }
    try {
      String encrypt = EncryptUtil.pbkdf2Hash(telephone, userModel.getSalt());
      if (encrypt.equals(userModel.getPassword())) {
        throw new BusinessException(BusinessEnum.LOGIN_FAIL);
      }
    } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
      throw new BusinessException(BusinessEnum.LOGIN_FAIL);
    }
    return userModel;
  }
}
