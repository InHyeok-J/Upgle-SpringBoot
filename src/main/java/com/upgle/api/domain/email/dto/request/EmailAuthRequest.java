package com.upgle.api.domain.email.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EmailAuthRequest {

  @NotBlank
  @Email
  private String email;
}
