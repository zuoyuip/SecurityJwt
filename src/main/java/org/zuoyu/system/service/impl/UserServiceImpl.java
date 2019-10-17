package org.zuoyu.system.service.impl;

import org.springframework.data.domain.Example;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;
import org.zuoyu.security.model.User;
import org.zuoyu.system.dao.UserMapper;
import org.zuoyu.system.service.IUserService;

/**
 * .
 *
 * @author zuoyu
 * @program jwt
 * @create 2019-10-15 23:09
 **/
@Service
class UserServiceImpl implements IUserService {

  private final UserMapper userMapper;

  public UserServiceImpl(UserMapper userMapper) {
    this.userMapper = userMapper;
  }

  @Override
  public User findUserByUserName(String userName) {
    return userMapper.findUserByUserName(userName).orElseThrow(RuntimeException::new);
  }

  @Override
  public boolean insertUser(User user) {
    if (user == null) {
      return false;
    }
    String admin = "admin";
    if (admin.equals(user.getUsername())){
      user.setRoles("USER,ADMIN").setUserStatus(true);
    }else {
      user.setRoles("USER").setUserStatus(true);
    }
    String passWord = user.getPassword();
    user.setPassWord(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(passWord));
    User s = userMapper.save(user);
    return user.equals(s);
  }

  @Override
  public boolean isUserNameExists(String userName) {
    Example<User> userExample = Example.of(new User().setUserName(userName));
    return userMapper.exists(userExample);
  }

  @Override
  public User getUserById(String userId) {
    return userMapper.findById(userId).orElseThrow(RuntimeException::new);
  }
}
