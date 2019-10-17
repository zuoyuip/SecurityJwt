package org.zuoyu.security.constants;

import java.util.UUID;

/**
 * JWT常量值.
 *
 * @author zuoyu
 * @program jwt
 * @create 2019-10-16 09:08
 **/
public final class JwtConstants {

  /**
   * 登录URL
   */
  public static String USER_LOGIN_URL = "/user/login";

  /**
   * 账户登录名称参数
   */
  public static String USER_LOGIN_USERNAME = "userName";

  /**
   * 账户登录名称参数
   */
  public static String USER_LOGIN_PASSWORD = "passWord";

  /**
   * 账户登录记住我参数
   */
  public static String USER_LOGIN_REMEMBER_ME = "rememberMe";

  /**
   * 账户登出URL
   */
  public static String USER_LOGOUT_URL = "/user/logout";

  /**
   * JWT颁发人
   */
  public static String JWT_ISSUER = "zuoyu";

  /**
   * ROLE-key
   */
  public static String ROLE_CLAIMS = "roles";

  /**
   * rememberMe 为 false 的时候过期时间是1个小时
   */
  public static long EXPIRATION = 60L * 60L;

  /**
   * rememberMe 为 true 的时候过期时间是7天
   */
  public static long EXPIRATION_REMEMBER = 60L * 60L * 24L * 7L;

  /**
   * JWT签名密钥
   */
  public static String JWT_SECRET_KEY = "C*F-JaNdRgUkXn2r5u8x/A?D(G+KbPeShVmYq3s6v9y$B&E)H@McQfTjWnZr4u7w";

  /**
   * JWT token在头文件的名字
   */
  public static String TOKEN_HEADER = "Authorization";

  /**
   * 类型
   */
  public static String TOKEN_TYPE = "JWT";

  private JwtConstants() {
  }

  /**
   * 获取UUID
   *
   * @return - UUID
   */
  public static String createTokenId() {
    return UUID.randomUUID().toString().replaceAll("-", "");
  }
}
