package com.upgle.api.domain.email.dto.response;

import lombok.Getter;

@Getter
public class EmailAuthResponse {

  private String message;

  public EmailAuthResponse(String message) {
    this.message = message;
  }
}
