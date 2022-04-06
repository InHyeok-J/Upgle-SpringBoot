package com.upgle.api.common.dto;

import lombok.Getter;

@Getter
public class BaseResponse {

  private String message;

  public BaseResponse(String message) {
    this.message = message;
  }
}
