package com.upgle.api.exception.dto;

import com.upgle.api.exception.ErrorCode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Getter
public class ErrorResponse {

  private final int status;
  private final String message;
  private final List<String> errors;

  private ErrorResponse(ErrorCode errorCode) {
    this.status = errorCode.getStatusCode();
    this.message = errorCode.getMessage();
    this.errors = new ArrayList<>();
  }

  private ErrorResponse(ErrorCode errorCode, String message) {
    this.status = errorCode.getStatusCode();
    this.message = message;
    this.errors = new ArrayList<>();
  }

  private ErrorResponse(ErrorCode errorCode, List<String> errors) {
    this.status = errorCode.getStatusCode();
    this.message = errorCode.getMessage();
    this.errors = errors;
  }

  public static ErrorResponse of(ErrorCode errorCode, String message) {
    return new ErrorResponse(errorCode, message);
  }

  public static ErrorResponse of(MethodArgumentNotValidException e) {
    // validation Fail(DTO)
    List<String> errors = checkError(e.getBindingResult());
    return new ErrorResponse(ErrorCode.INVALID_INPUT_VALUE, errors);
  }

  private static List<String> checkError(BindingResult bindingResult) {
    List<FieldError> fieldErrors = bindingResult.getFieldErrors();
    return fieldErrors.stream()
        .map(error -> new String(
            "[" +
                error.getField() +
                "](은)는 " +
                error.getDefaultMessage() +
                " 입력된 값: [" +
                error.getRejectedValue() +
                "]"
        )).collect(Collectors.toList());
  }
}
