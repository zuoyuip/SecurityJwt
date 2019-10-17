package org.zuoyu.system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zuoyu.security.model.User;
import org.zuoyu.security.service.ISecurityService;

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

  private final ISecurityService iSecurityService;

  public UserController(ISecurityService iSecurityService) {
    this.iSecurityService = iSecurityService;
  }


  @GetMapping("/me")
  @PreAuthorize("authentication")
  public ResponseEntity<User> getMyself() {
    User user = iSecurityService.currentUser();
    return ResponseEntity.ok(user);
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<User> testAdmin() {
    User user = iSecurityService.currentUser();
    return ResponseEntity.ok(user);
  }
}
