package com.yokit.resource_management.common.exception;

import com.yokit.resource_management.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author tonylin
 * @create 2024/6/30 15:17
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

  /**
   * 处理空指针异常
   */
  @ExceptionHandler(NullPointerException.class)
  public ResponseDto nullPointerExceptionHandler(NullPointerException ex) {
    log.error("发生空指针异常，原因是：" + ex);
    return new ResponseDto(400, "请求参数错误", ex);
  }

  /**
   * 其他异常处理
   * @param ex
   * @return
   */
  @ExceptionHandler(Exception.class)
  public ResponseDto exceptionHandler(Exception ex) {
    log.error("服务器内部错误,原因是：" + ex);
    return new ResponseDto(500, "服务器内部错误", ex);
  }
}
