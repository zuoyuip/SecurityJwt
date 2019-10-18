package org.zuoyu.jwt.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zuoyu.jwt.security.model.User;
import org.zuoyu.jwt.system.service.IUserService;
import org.zuoyu.jwt.system.util.Result;

/**
 * 用户操作.
 *
 * @author zuoyu
 * @program jwt
 * @create 2019-10-16 21:45
 **/
@RestController
@RequestMapping("/user")
public class AuthController {

  private final IUserService iUserService;

  public AuthController(IUserService iUserService) {
    this.iUserService = iUserService;
  }

  @PostMapping("/register")
  public ResponseEntity<Result> register(User user) {
    boolean isExists = iUserService.isUserNameExists(user.getUsername());
    if (isExists) {
      return ResponseEntity.status(HttpStatus.CREATED).body(Result.message("该帐号已注册"));
    }
    boolean isOk = iUserService.insertUser(user);
    if (isOk) {
      return ResponseEntity.ok(Result.message("注册成功"));
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.message("注册失败"));
  }

}
