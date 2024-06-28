package com.yokit.resource_management.dto;

/**
 * @author tonylin
 * @create 2024/6/30 15:17
 */
public class ResponseDto<T> {
  private String msg;
  private Integer status;
  private T data;

  public ResponseDto() {

  }

  @SafeVarargs
  public ResponseDto(T... data) {
    this.msg = "操作成功";
    this.status = 200;
    if (data.length > 0) {
      this.data = data[0];
    }
  }

  public ResponseDto(Integer status,String msg, T data) {
    this.status = status;
    this.msg = msg;
    this.data = data;
  }

  public ResponseDto(Integer status,String msg) {
    this.status = status;
    this.msg = msg;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
}
