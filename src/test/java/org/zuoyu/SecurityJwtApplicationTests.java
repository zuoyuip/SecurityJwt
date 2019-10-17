package org.zuoyu;

import java.util.Collection;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;
import org.zuoyu.security.model.User;
import org.zuoyu.security.utils.JwtTokenUtils;

//@RunWith(SpringRunner.class)
//@SpringBootTest
//@WebAppConfiguration
public class SecurityJwtApplicationTests {

  @Test
  public void contextLoads() {
  }

  @Test
  public void testJwt() {
    User user = new User(1, "zuoyu", "root", true, "USER,ADMIN");
    String token = JwtTokenUtils.createToken(user);
    Collection<? extends GrantedAuthority> authorityList = JwtTokenUtils.getAuthoritiesByToken(token);
    authorityList.forEach(System.out::println);
  }


}
