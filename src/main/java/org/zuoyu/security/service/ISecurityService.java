package org.zuoyu.security.service;

import org.zuoyu.security.model.User;

/**
 * 用户操作.
 *
 * @author zuoyu
 * @program jwt
 * @create 2019-10-17 17:43
 **/
public interface ISecurityService {

  /**
   * 实时用户信息
   * @return - JwtUser
   */
  User realTimeUser();

  /**
   * 当前用户信息
   * @return - JwtUser
   */
  org.zuoyu.security.model.User currentUser();
}
