package org.zuoyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.zuoyu.security.config.SecurityConfig;
import org.zuoyu.security.config.WebConfig;

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
