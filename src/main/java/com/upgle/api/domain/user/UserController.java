package com.upgle.api.domain.user;

import com.upgle.api.domain.user.dto.request.UserSignUpRequest;
import com.upgle.api.domain.user.dto.response.UserSignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserController {

  private final UserService userService;

  //일단은 TEST
  @GetMapping("/api/user/{id}")
  public Long findById(@PathVariable Long id) {
    return userService.findById(id);
  }

  //회원가입
  @PostMapping("/api/user")
  public ResponseEntity<UserSignUpResponse> singUp(
      @Valid @RequestBody UserSignUpRequest signUpRequest) {
    return ResponseEntity.ok().body(userService.signUp(signUpRequest));
  }
}
