package com.upgle.api.domain.email;

import com.upgle.api.domain.cache.CacheService;
import com.upgle.api.domain.email.dto.request.EmailAuthRequest;
import com.upgle.api.domain.email.dto.request.EmailCheckRequest;
import com.upgle.api.domain.user.UserRepository;
import com.upgle.api.exception.errors.AuthenticationFailException;
import com.upgle.api.exception.errors.DuplicateResourceException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailService {

  private final JavaMailSender javaMailSender;
  private final CacheService cacheService;
  private final UserRepository userRepository;

  public String emailSend(EmailAuthRequest request) {
    if (userRepository.existsUserByEmailAndSnsType(request.getEmail(), "Local")) {
      // 이미 계정이 존재하면 (SNS TYPE = local 인 경우)
      throw new DuplicateResourceException(request.getEmail());
    }
    //pre:checked -> 이메일 전송 후 인증안됨 -> 재인증 및 캐시 값 재설정
    //checked  -> 이메일 전송후 인증까지 완료됨 -> 인증된 유저라고 return
    if ("checked".equals(cacheService.getCacheString(request.getEmail()))) {
      return "이미 인증된 회원입니다.";
    } else {
      SimpleMailMessage message = new SimpleMailMessage();
      String uuid = UUID.randomUUID().toString();
      message.setTo(request.getEmail()); //받을 사람
      message.setSubject("UPGLE 인증메일입니다."); // 제목
      message.setText(uuid); // 본문
      javaMailSender.send(message);

      // key : email주소, value : pre:checked, expired TIme : 3분
      cacheService.setCacheString(request.getEmail(), "pre:checked", 180);
      cacheService.setCacheString(request.getEmail() + "code", uuid, 180);
      return "메일 전송완료";
    }
  }

  public String emailCheck(EmailCheckRequest request) {
    if ("pre:checked".equals(cacheService.getCacheString(request.getEmail()))) {
      String cachedCode = cacheService.getCacheString((request.getEmail() + "code"));
      if (cachedCode.equals(request.getCode())) {

        cacheService.setCacheString(request.getEmail(), "checked", 600);//10분
        return "인증 성공!";
      } else {
        throw new AuthenticationFailException("이메일 code");
      }
    } else {
      throw new AuthenticationFailException("이메일 인증이 필요합니다.");
    }
  }
}
