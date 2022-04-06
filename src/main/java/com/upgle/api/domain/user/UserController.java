package com.upgle.api.domain.user;

import com.upgle.api.common.dto.CommonResponse;
import com.upgle.api.domain.user.dto.request.UserSignUpRequest;
import com.upgle.api.domain.user.dto.response.UserSignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/user")
@RequiredArgsConstructor
@RestController
public class UserController {

  private final UserService userService;

  //일단은 TEST
  @GetMapping("/{id}")
  public Long findById(@PathVariable Long id) {
    return userService.findById(id);
  }

  //회원가입
  @PostMapping("")
  public ResponseEntity<CommonResponse<?>> singUp(
      @Valid @RequestBody UserSignUpRequest signUpRequest) {
    CommonResponse<UserSignUpResponse> response = new CommonResponse<>(
        new UserSignUpResponse(userService.signUp(signUpRequest)));
    return ResponseEntity.ok().body(response);
  }
}
