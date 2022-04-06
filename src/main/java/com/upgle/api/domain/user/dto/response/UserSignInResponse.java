package com.upgle.api.domain.user.dto.response;

import com.upgle.api.common.dto.BaseResponse;
import lombok.Getter;

@Getter
public class UserSignInResponse extends BaseResponse {

  private String accessToken;

  public UserSignInResponse(String token) {
    super("로그인 성공");
    this.accessToken = token;
  }
}
