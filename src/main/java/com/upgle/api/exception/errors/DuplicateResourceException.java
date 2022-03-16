package com.upgle.api.exception.errors;

import com.upgle.api.exception.ErrorCode;

public class DuplicateResourceException extends BusinessException {

  public DuplicateResourceException(String resource) {
    super((resource + "[중복]"), ErrorCode.DUPLICATE_RESOURCE);
  }

}
