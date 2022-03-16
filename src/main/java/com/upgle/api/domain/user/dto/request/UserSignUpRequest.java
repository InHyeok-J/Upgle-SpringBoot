package com.upgle.api.domain.user.dto.request;

import com.upgle.api.domain.user.User;
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

  public User toEntity(String hashedPassword) {
    return User.builder()
        .email(this.email)
        .nickname(this.nickname)
        .password(hashedPassword)
        .snsType("Local")
        .build();
  }
}
