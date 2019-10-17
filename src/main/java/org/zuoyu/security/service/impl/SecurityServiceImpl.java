package org.zuoyu.security.service.impl;

import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.zuoyu.security.model.User;
import org.zuoyu.security.service.ISecurityService;
import org.zuoyu.system.service.IUserService;

/**
 * .
 *
 * @author zuoyu
 * @program jwt
 * @create 2019-10-17 17:48
 **/
@Service
class SecurityServiceImpl implements ISecurityService {

  private final IUserService iUserService;

  SecurityServiceImpl(IUserService iUserService) {
    this.iUserService = iUserService;
  }

  @Override
  public User realTimeUser() {
    return iUserService.getUserById(currentUser().getUserId().toString());
  }

  @Override
  public User currentUser() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    Authentication authentication = securityContext.getAuthentication();
    if (!authentication.isAuthenticated()) {
      return null;
    }
    return Optional.ofNullable((User) authentication.getPrincipal())
        .orElseThrow(RuntimeException::new);
  }
}
