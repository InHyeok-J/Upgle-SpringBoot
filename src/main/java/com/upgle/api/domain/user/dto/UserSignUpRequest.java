package com.upgle.api.domain.user.dto;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class UserSignUpRequest {

  @Email
  @NotBlank
  private String email;

  @NotBlank
  private String nickname;

  @NotBlank
  private String password;
}
