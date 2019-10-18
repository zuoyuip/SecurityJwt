package org.zuoyu.jwt.system.exception;

import java.io.IOException;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zuoyu.jwt.system.util.Result;

/**
 * 异常拦截.
 *
 * @author zuoyu
 * @program jwt
 * @create 2019-10-16 21:52
 **/
@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

  /**
   * 自定义异常
   */
  @ExceptionHandler(CustomException.class)
  public ResponseEntity<Result> handlerCustomException(CustomException e) {
    return exceptionFormat(e.getCode(), e.getMessage());
  }

  /**
   * 空指针异常
   */
  @ExceptionHandler(NullPointerException.class)
  public ResponseEntity<Result> handlerNullPointerException(NullPointerException e) {
    return exceptionFormat(e.getMessage());
  }

  /**
   * sql异常
   */
  @ExceptionHandler(SQLException.class)
  public ResponseEntity<Result> handlerSqlException(SQLException e) {
    return exceptionFormat(e.getErrorCode(), e.getMessage());
  }

  /**
   * 类型转换异常
   */
  @ExceptionHandler(ClassCastException.class)
  public ResponseEntity<Result> handlerClassCastException(ClassCastException e) {
    return exceptionFormat(e.getMessage());
  }

  /**
   * IO异常
   */
  @ExceptionHandler(IOException.class)
  public ResponseEntity<Result> handlerIoException(IOException e) {
    return exceptionFormat(e.getMessage());
  }

  /**
   * 未知方法异常
   */
  @ExceptionHandler(NoSuchMethodException.class)
  public ResponseEntity<Result> handlerNoSuchMethodException(NoSuchMethodException e) {
    return exceptionFormat(e.getMessage());
  }

  /**
   * 数组越界异常
   */
  @ExceptionHandler(IndexOutOfBoundsException.class)
  public ResponseEntity<Result> handlerIndexOutOfBoundsException(IndexOutOfBoundsException e) {
    return exceptionFormat(e.getMessage());
  }

  /**
   * 栈溢出异常
   */
  @ExceptionHandler(StackOverflowError.class)
  public ResponseEntity<Result> handlerStackOverflowError(StackOverflowError e) {
    return exceptionFormat(e.getMessage());
  }


  /**
   * 格式化
   */
  private ResponseEntity<Result> exceptionFormat(String message) {
    log.error("errorMessage is : " + message);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.message("服务器异常"));
  }

  private ResponseEntity<Result> exceptionFormat(int code, String message) {
    log.error("errorCode is : " + code + "\t" + "errorMessage is : " + message);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.message("服务器异常"));
  }
}
