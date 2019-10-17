package org.zuoyu.system.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * 返回集包装.
 *
 * @author zuoyu
 * @program jwt
 * @create 2019-10-16 21:50
 **/
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Result {

  @NonNull
  private String message;

  private Object data;


  /**
   * 响应信息
   *
   * @param message - 信息
   * @return - 结果
   */
  public static Result message(String message) {
    return new Result(message);
  }

  /**
   * 响应信息和数据
   *
   * @param message - 信息
   * @param data - 数据
   * @return - 结果
   */
  public static Result detail(String message, Object data) {
    return new Result(message, data);
  }
}
