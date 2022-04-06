package com.upgle.api.domain.email;


import com.upgle.api.common.dto.BaseResponse;
import com.upgle.api.common.dto.CommonResponse;
import com.upgle.api.domain.email.dto.request.EmailAuthRequest;
import com.upgle.api.domain.email.dto.request.EmailCheckRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailController {

  private final EmailService emailService;

  @PostMapping("/api/email")
  public ResponseEntity<CommonResponse<?>> sendEmail(@RequestBody @Valid EmailAuthRequest dto) {
    CommonResponse<BaseResponse> response = new CommonResponse<>(
        new BaseResponse(emailService.emailSend(dto)));
    return ResponseEntity.ok().body(response);
  }

  @PostMapping("/api/email/check")
  public ResponseEntity<CommonResponse<?>> checkCode(@RequestBody @Valid EmailCheckRequest dto) {
    CommonResponse<BaseResponse> response = new CommonResponse<>(
        new BaseResponse(emailService.emailCheck(dto)));
    return ResponseEntity.ok().body(response);
  }
}