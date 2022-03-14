package com.upgle.api.domain.email;


import com.upgle.api.domain.cache.CacheService;
import com.upgle.api.domain.email.dto.request.EmailAuthRequest;
import com.upgle.api.domain.email.dto.request.EmailCheckRequest;
import com.upgle.api.domain.email.dto.response.EmailAuthResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailController {

  private final EmailService emailService;

  @PostMapping("/api/email")
  public ResponseEntity<EmailAuthResponse> sendEmail(@RequestBody @Valid EmailAuthRequest dto) {
    return ResponseEntity.ok().body(emailService.emailSend(dto));
  }

  @PostMapping("/api/email/check")
  public ResponseEntity<EmailAuthResponse> checkCode(@RequestBody @Valid EmailCheckRequest dto) {
    return ResponseEntity.ok().body(emailService.emailCheck(dto));
  }
}