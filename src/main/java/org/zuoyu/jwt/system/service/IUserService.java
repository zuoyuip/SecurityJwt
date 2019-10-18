package org.zuoyu.jwt.system.service;

import org.zuoyu.jwt.security.model.User;

/**
 * User服务.
 *
 * @author zuoyu
 * @program jwt
 * @create 2019-10-15 23:08
 **/
public interface IUserService {

  /**
   * 根据账户名称查询对应账户
   *
   * @param userName - 账户名称
   * @return - 账户
   */
  User findUserByUserName(String userName);

  /**
   * 添加用户
   * @param user - 用户
   * @return - false/true
   */
  boolean insertUser(User user);

  /**
   * 检测该用户名是否已存在
   * @param userName - 用户名
   * @return - false/true
   */
  boolean isUserNameExists(String userName);

  /**
   * 根据Id获取User
   * @param userId - Id
   * @return - User
   */
  User getUserById(String userId);
}
