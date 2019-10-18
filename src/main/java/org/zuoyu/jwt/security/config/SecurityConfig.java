package org.zuoyu.jwt.security.config;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsUtils;
import org.zuoyu.jwt.security.constants.JwtConstants;
import org.zuoyu.jwt.security.filter.JwtAuthorizationFilter;
import org.zuoyu.jwt.security.handler.AuthenticationFailureHandlerImpl;
import org.zuoyu.jwt.security.handler.AuthenticationSuccessHandlerImpl;

/**
 * 权限配置.
 *
 * @author zuoyu
 * @program jwt
 * @create 2019-10-16 17:29
 **/
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsService userDetailsService;

  public SecurityConfig(
      @Qualifier("userDetailsService") UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @Bean("passwordEncoder")
  @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService);
    super.configure(auth);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .cors()
        .and()
        .csrf().disable()
        .requestCache().disable()
        .authorizeRequests()
        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
        .antMatchers().authenticated()
        .anyRequest().permitAll()
        .and().addFilter(new JwtAuthorizationFilter(this.authenticationManager()))
        .formLogin().loginProcessingUrl(JwtConstants.USER_LOGIN_URL)
        .usernameParameter(JwtConstants.USER_LOGIN_USERNAME)
        .passwordParameter(JwtConstants.USER_LOGIN_PASSWORD)
        .permitAll()
        .successHandler(new AuthenticationSuccessHandlerImpl())
        .failureHandler(new AuthenticationFailureHandlerImpl())
        .and()
        .rememberMe()
        .rememberMeParameter(JwtConstants.USER_LOGIN_REMEMBER_ME)
        .disable()
        .logout().logoutUrl(JwtConstants.USER_LOGOUT_URL)
        .logoutSuccessHandler(new LogoutSuccessHandlerImpl()).clearAuthentication(true).permitAll()
        .and()
        .exceptionHandling()
        .accessDeniedHandler(new AccessDeniedHandlerImpl())
        .authenticationEntryPoint(new AuthenticationEntryPointImpl())
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  /**
   * 用来解决认证过的用户访问无权限资源时的异常.
   **/
  @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  static class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse, AccessDeniedException e)
        throws IOException {
      httpServletResponse.setContentType("application/json;charset=utf-8");
      httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "对不起，您没有访问权限");
    }
  }

  /**
   * 用来解决匿名用户访问无权限资源时的异常.
   **/
  @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  static class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse, AuthenticationException e)
        throws IOException {
      httpServletResponse.setContentType("application/json;charset=utf-8");
      httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "您还没有登录，请先登录");
    }
  }

  /**
   * 注销成功的实现.
   **/
  @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  static class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse, Authentication authentication)
        throws IOException {
      httpServletResponse.setContentType("application/json;charset=utf-8");
      httpServletResponse.setStatus(HttpServletResponse.SC_OK);
      PrintWriter responseWriter = httpServletResponse.getWriter();
      responseWriter.write("{\"message\":\"注销成功\"}");
      responseWriter.flush();
      responseWriter.close();
    }
  }
}
