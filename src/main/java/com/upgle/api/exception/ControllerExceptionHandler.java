package com.upgle.api.exception;

import com.upgle.api.exception.dto.ErrorResponse;
import com.upgle.api.exception.errors.AuthenticationFailException;
import com.upgle.api.exception.errors.BusinessException;
import com.upgle.api.exception.errors.DuplicateResourceException;
import com.upgle.api.exception.errors.NotFoundResourceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice // 에러 컨트롤
public class ControllerExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<ErrorResponse> validationError(
      MethodArgumentNotValidException e) {
    log.error("validation Fail");
    ErrorResponse response = ErrorResponse.of(e);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {
      NotFoundResourceException.class,
      DuplicateResourceException.class,
      AuthenticationFailException.class
  })
  public ResponseEntity<ErrorResponse> Exception(BusinessException e) {
    ErrorResponse response = ErrorResponse.of(e.getErrorCode(), e.getMessage());
    return new ResponseEntity<>(response, HttpStatus.valueOf(e.getErrorCode().getStatusCode()));
  }
}
