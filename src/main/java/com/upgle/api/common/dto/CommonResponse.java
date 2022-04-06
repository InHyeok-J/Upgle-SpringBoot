package com.upgle.api.common.dto;

import lombok.Getter;

@Getter
public class CommonResponse<T> {

  private T data;

  public CommonResponse(T data) {
    this.data = data;
  }
}
