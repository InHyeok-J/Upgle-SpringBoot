package com.upgle.api.exception.errors;

import com.upgle.api.exception.ErrorCode;

public class NotFoundResourceException extends BusinessException {


  public NotFoundResourceException(String resource) {
    super((resource + "[찾을 수 없습니다.]"), ErrorCode.NOT_FOUND_RESOURCE);
  }

}
