package org.zuoyu.jwt.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.zuoyu.jwt.security.constants.JwtConstants;
import org.zuoyu.jwt.security.model.User;

/**
 * JWT_Token的工具类.
 *
 * @author zuoyu
 * @program jwt
 * @create 2019-10-16 09:38
 **/
public class JwtTokenUtils {

  private final static byte[] API_KEY_SECRET_BYTES = DatatypeConverter
      .parseBase64Binary(JwtConstants.JWT_SECRET_KEY);

  private final static SecretKey SECRET_KEY = Keys.hmacShaKeyFor(API_KEY_SECRET_BYTES);


  private JwtTokenUtils() {
  }


  /**
   * 根据账户构建token
   *
   * @param user - 账户
   * @return -
   */
  public static String createToken(User user, boolean isRememberMe) {
    long expiration =
        isRememberMe ? JwtConstants.EXPIRATION_REMEMBER : JwtConstants.EXPIRATION;
    String spacer = ",";
    List<String> authorities = Arrays.stream(user.getRoles().split(spacer))
        .map(role -> "ROLE_" + role)
        .collect(Collectors.toList());
    return createJwt(JsonUtil.beanToJsonString(user), JsonUtil.objectToJsonString(authorities),
        expiration);
  }

  /**
   * 获取用户
   *
   * @param token - token
   * @return - User
   */
  public static User getUserByToken(String token) {
    String subject = parseJwt(token).getSubject();
    return JsonUtil.jsonStringToBean(subject, User.class);
  }

  /**
   * 获取用户的权限
   * @param token - token
   * @return - 权限列表
   */
  public static Collection<? extends GrantedAuthority> getAuthoritiesByToken(String token) {
    String roles = parseJwt(token).get(JwtConstants.ROLE_CLAIMS).toString();
    return JsonUtil.jsonStringToCollection(roles, SimpleGrantedAuthority.class);
  }

  /**
   * 是否已过期
   *
   * @param token - token
   * @return boolean
   */
  public static boolean isTokenExpired(String token) {
    return parseJwt(token).getExpiration().before(new Date());
  }

  /**
   * 构建JWT
   *
   * @param subject - 实体
   * @param authorities - 权限
   * @param expiration - 保留时间
   * @return - token
   */
  private static String createJwt(String subject,
      String authorities, long expiration) {
    long nowMillis = System.currentTimeMillis();
    return Jwts.builder()
        .setId(JwtConstants.createTokenId())
        .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
        .setIssuer(JwtConstants.JWT_ISSUER)
        .setSubject(subject)
        .claim(JwtConstants.ROLE_CLAIMS, authorities)
        .setIssuedAt(new Date(nowMillis))
        .setNotBefore(new Date(nowMillis))
        .setExpiration(new Date(nowMillis + expiration * 1000L))
        .compact();
  }

  /**
   * 解析token
   *
   * @param token -
   * @return - Claims
   */
  private static Claims parseJwt(String token) {
    return Jwts.parser()
        .setSigningKey(SECRET_KEY)
        .parseClaimsJws(token)
        .getBody();
  }
}
