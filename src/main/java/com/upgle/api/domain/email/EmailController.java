package com.upgle.api.domain.email;


import com.upgle.api.common.dto.BaseResponse;
import com.upgle.api.common.dto.SuccessResponse;
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
  public ResponseEntity<?> sendEmail(@RequestBody @Valid EmailAuthRequest dto) {

    return SuccessResponse.ok(new BaseResponse(emailService.emailSend(dto)));
  }

  @PostMapping("/api/email/check")
  public ResponseEntity<?> checkCode(@RequestBody @Valid EmailCheckRequest dto) {

    return SuccessResponse.ok(new BaseResponse(emailService.emailCheck(dto)));
  }
}