package com.upgle.api.domain.user.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class UserSignInRequest {

  @Email
  @NotBlank
  private String email;

  @NotBlank
  @Pattern(regexp = "^[a-zA-Z0-9]*$")
  @Length(min = 8, max = 20)
  private String password;
}
