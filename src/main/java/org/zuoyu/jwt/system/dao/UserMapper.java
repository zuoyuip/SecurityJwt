package org.zuoyu.jwt.system.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zuoyu.jwt.security.model.User;

/**
 * User的JPA.
 *
 * @author zuoyu
 * @program jwt
 * @create 2019-10-15 23:03
 **/
@Repository
public interface UserMapper extends JpaRepository<User, String> {

  /**
   * 根据账户名称查询对应账户
   * @param userName - 账户名称
   * @return - 账户
   */
  Optional<User> findUserByUserName(String userName);

}
