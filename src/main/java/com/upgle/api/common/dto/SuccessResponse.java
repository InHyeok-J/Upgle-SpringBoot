package com.upgle.api.common.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class SuccessResponse {

  private final Object data;

  private SuccessResponse(Object data) {
    this.data = data;
  }

  public static ResponseEntity<?> ok(Object data) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(new SuccessResponse(data));
  }
}
