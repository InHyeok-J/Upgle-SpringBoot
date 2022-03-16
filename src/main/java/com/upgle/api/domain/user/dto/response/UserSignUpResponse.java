package com.upgle.api.domain.user.dto.response;

import com.upgle.api.domain.user.User;
import lombok.Getter;

@Getter
public class UserSignUpResponse {

  private Long id;
  private String email;
  private String nickname;

  public UserSignUpResponse(User user) {
    this.id = user.getId();
    this.email = user.getEmail();
    this.nickname = user.getNickname();
  }
}
