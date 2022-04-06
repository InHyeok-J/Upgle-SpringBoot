package com.upgle.api.domain.user;

import com.upgle.api.common.dto.AuthUser;
import com.upgle.api.common.dto.SuccessResponse;
import com.upgle.api.config.security.LoginUser;
import com.upgle.api.domain.user.dto.request.UserSignInRequest;
import com.upgle.api.domain.user.dto.request.UserSignUpRequest;
import com.upgle.api.domain.user.dto.response.UserResponse;
import com.upgle.api.domain.user.dto.response.UserSignInResponse;
import com.upgle.api.domain.user.dto.response.UserSignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/user")
@RequiredArgsConstructor
@RestController
public class UserController {

  private final UserService userService;

  //일단은 TEST
  @GetMapping("")
  public ResponseEntity<?> findById(@LoginUser AuthUser user) {
    User findUser = userService.findById(user.getId());

    return SuccessResponse.ok(new UserResponse("조회 성공", findUser));
  }

  //회원가입
  @PostMapping("")
  public ResponseEntity<?> singUp(
      @Valid @RequestBody UserSignUpRequest signUpRequest) {
    return SuccessResponse.ok(new UserSignUpResponse(userService.signUp(signUpRequest)));
  }

  //로그인
  @PostMapping("/signin")
  public ResponseEntity<?> signIn(
      @Valid @RequestBody UserSignInRequest signInRequest) {
    return SuccessResponse.ok(new UserSignInResponse(userService.SignIn(signInRequest)));
  }
}
