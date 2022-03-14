package com.upgle.api.domain.user;

import com.upgle.api.domain.user.dto.UserSignUpRequest;
import com.upgle.api.domain.user.dto.UserSignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  public Long findById(Long id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다 id:" + id));
    return user.getId();
  }

  public UserSignUpResponse signUp(UserSignUpRequest request) {
    if (userRepository.existsUserByNickname(request.getNickname())) {
      throw new IllegalArgumentException("닉네임 중복" + request.getNickname());
    }
    String hashPassword = passwordEncoder.encode(request.getPassword());
    User newUser = User.builder()
        .nickname(request.getNickname())
        .email(request.getEmail())
        .password(hashPassword)
        .build();

    User savedUser = userRepository.save(newUser);

    return new UserSignUpResponse(savedUser);
  }
}
