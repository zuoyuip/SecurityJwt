package org.zuoyu.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zuoyu.security.entity.JwtUser;
import org.zuoyu.security.model.User;
import org.zuoyu.system.service.IUserService;

/**
 * 自定义账户校验源.
 *
 * @author zuoyu
 * @program jwt
 * @create 2019-10-15 22:59
 **/
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

  private final IUserService iUserService;

  public UserDetailsServiceImpl(IUserService iUserService) {
    this.iUserService = iUserService;
  }

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    User user = iUserService.findUserByUserName(s);
    return new JwtUser(user);
  }
}
