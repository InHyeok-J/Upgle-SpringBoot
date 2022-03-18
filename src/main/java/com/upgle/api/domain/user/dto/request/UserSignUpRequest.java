package com.upgle.api.domain.user.dto.request;

import com.upgle.api.domain.user.User;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

@Getter
@AllArgsConstructor
public class UserSignUpRequest {

  @Email
  @NotBlank
  private String email;

  @NotBlank
  private String nickname;

  @Pattern(regexp = "^[a-zA-Z0-9]*$")
  @Length(min = 8, max = 20)
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
