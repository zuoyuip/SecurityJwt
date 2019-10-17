package org.zuoyu.security.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 用户.
 *
 * @author zuoyu
 * @program jwt
 * @create 2019-10-15 22:09
 **/
@Entity
@Data
@Accessors(chain = true)
@Table(name = "`TB_USER`")
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "USER_ID")
  private Integer userId;

  @Column(name = "USER_NAME")
  private String userName;

  @Column(name = "PASS_WORD")
  private String passWord;

  @Column(name = "USER_STATUS")
  private Boolean userStatus;

  @Column(name = "ROLES")
  private String roles;

  @Transient
  private boolean rememberMe;

  public User(Integer userId, String userName, String passWord, Boolean userStatus, String roles) {
    this.userId = userId;
    this.userName = userName;
    this.passWord = passWord;
    this.userStatus = userStatus;
    this.roles = roles;
  }
}
