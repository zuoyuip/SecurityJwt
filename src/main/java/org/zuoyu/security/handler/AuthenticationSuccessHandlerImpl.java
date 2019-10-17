package org.zuoyu.security.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.zuoyu.security.constants.JwtConstants;
import org.zuoyu.security.model.User;
import org.zuoyu.security.utils.JwtTokenUtils;

/**
 * 登录成功的实现.
 *
 * @author zuoyu
 **/
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException {
    String rememberMe = request.getParameter(JwtConstants.USER_LOGIN_REMEMBER_ME);
    boolean isRememberMe = Boolean.parseBoolean(rememberMe);
    User user = (User) authentication.getPrincipal();
    user.setRememberMe(isRememberMe);
    String token = JwtTokenUtils.createToken(user);
    response.setContentType("application/json;charset=utf-8");
    response.setHeader(JwtConstants.TOKEN_HEADER, token);
    response.setStatus(HttpServletResponse.SC_OK);
    PrintWriter responseWriter = response.getWriter();
    responseWriter.write("{\"message\":\"登录成功\"}");
    responseWriter.flush();
    responseWriter.close();
  }


}
