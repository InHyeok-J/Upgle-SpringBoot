package com.upgle.api.domain.user.dto;

import lombok.Getter;

@Getter
public class UserSignUpRequest {
    private String email;
    private String nickname;
    private String password;
}
