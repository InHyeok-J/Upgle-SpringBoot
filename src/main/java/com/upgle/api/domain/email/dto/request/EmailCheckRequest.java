package com.upgle.api.domain.email.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EmailCheckRequest {

  @NotBlank
  @Email
  private String email;

  @NotBlank
  private String code;
}
