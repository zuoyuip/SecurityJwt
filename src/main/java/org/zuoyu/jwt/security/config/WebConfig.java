package org.zuoyu.jwt.security.config;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.zuoyu.jwt.security.constants.JwtConstants;

/**
 * Web服务配置.
 *
 * @author zuoyu
 * @program jwt
 * @create 2019-10-16 17:20
 **/
public class WebConfig extends WebMvcConfigurationSupport {

  @Override
  protected void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedHeaders(CorsConfiguration.ALL)
        .allowedMethods(CorsConfiguration.ALL)
        .allowedOrigins(CorsConfiguration.ALL)
        .allowCredentials(true)
        //暴露header中的属性给客户端应用程序，否则无法获取token
        .exposedHeaders(JwtConstants.TOKEN_HEADER)
        .maxAge(3600L);
    super.addCorsMappings(registry);
  }
}
