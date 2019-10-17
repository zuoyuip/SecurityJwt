package org.zuoyu.security.entity;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.zuoyu.security.model.User;

/**
 * JWT用户.
 *
 * @author zuoyu
 * @program jwt
 * @create 2019-10-15 22:31
 **/
public class JwtUser extends User implements UserDetails {


  public JwtUser(Integer userId, String userName, String passWord,
      Boolean userStatus, String roles) {
    super(userId, userName, passWord, userStatus, roles);
  }

  /**
   * @param user - 账户
   */
  public JwtUser(User user) {
    super(
        user.getUserId(),
        user.getUserName(),
        user.getPassWord(),
        user.getUserStatus(),
        user.getRoles()
    );
  }

  public JwtUser() {
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    String spacer = ",";
    return Arrays.stream(super.getRoles().split(spacer))
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
        .collect(Collectors.toList());
  }

  @Override
  public String getPassword() {
    return super.getPassWord();
  }

  @Override
  public String getUsername() {
    return super.getUserName();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return super.getUserStatus();
  }
}
