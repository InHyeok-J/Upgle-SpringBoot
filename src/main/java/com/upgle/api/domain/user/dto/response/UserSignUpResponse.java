package com.upgle.api.domain.user.dto.response;

import com.upgle.api.common.dto.BaseResponse;
import com.upgle.api.domain.user.User;
import lombok.Getter;

@Getter
public class UserSignUpResponse extends BaseResponse {

  private Long id;
  private String email;
  private String nickname;

  public UserSignUpResponse(User user) {
    super("회원가입 성공");
    this.id = user.getId();
    this.email = user.getEmail();
    this.nickname = user.getNickname();
  }
}
