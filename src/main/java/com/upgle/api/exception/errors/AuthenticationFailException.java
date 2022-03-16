package com.upgle.api.exception.errors;

import com.upgle.api.exception.ErrorCode;

public class AuthenticationFailException extends BusinessException {

  public AuthenticationFailException(String message) {
    super((message + "[인증 실패]"), ErrorCode.AUTHORIZATION_FAIL);
  }
}
