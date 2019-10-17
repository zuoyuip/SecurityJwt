package org.zuoyu.security.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zuoyu.system.service.IUserService;

/**
 * 自定义账户校验源.
 *
 * @author zuoyu
 * @program jwt
 * @create 2019-10-15 22:59
 **/
@Slf4j
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

  private final IUserService iUserService;

  public UserDetailsServiceImpl(IUserService iUserService) {
    this.iUserService = iUserService;
  }

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    return iUserService.findUserByUserName(s);
  }
}
