package org.zuoyu.jwt.system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户测试接口.
 *
 * @author zuoyu
 * @program jwt
 * @create 2019-10-17 17:36
 **/
@RestController
@RequestMapping("/api")
public class UserController {


  @GetMapping("/anonymousUser")
  public ResponseEntity<Object> getMyselfOne() {
    return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication());
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/authentication")
  public ResponseEntity<Object> getMyselfTwo() {
    return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication());
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("/admin")
  public ResponseEntity<Object> getMyselfThree() {
    return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication());
  }
}
