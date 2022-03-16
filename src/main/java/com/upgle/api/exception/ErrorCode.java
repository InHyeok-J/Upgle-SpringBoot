package com.upgle.api.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

  //COMMON
  INVALID_INPUT_VALUE(400, " input value is invalid"),
  NOT_FOUND_RESOURCE(404, " resource not found"),
  DUPLICATE_RESOURCE(400, " duplicate resource and request fail"),
  AUTHORIZATION_FAIL(401, " authorization fail");

  //Other..

  private final int statusCode;
  private final String message;

  ErrorCode(int statusCode, String message) {
    this.statusCode = statusCode;
    this.message = message;
  }
}
