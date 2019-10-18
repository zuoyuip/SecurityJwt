package org.zuoyu.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.zuoyu.jwt.security.config.SecurityConfig;
import org.zuoyu.jwt.security.config.WebConfig;

/**
 * @author zuoyu
 */
@Import(value = {
    SecurityConfig.class, WebConfig.class
})
@SpringBootApplication
public class SecurityJwtApplication {

  public static void main(String[] args) {
    SpringApplication.run(SecurityJwtApplication.class, args);
  }

}
