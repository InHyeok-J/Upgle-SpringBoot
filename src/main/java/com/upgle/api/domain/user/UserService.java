package com.upgle.api.domain.user;

import com.upgle.api.config.jwt.JwtProvider;
import com.upgle.api.domain.cache.CacheService;
import com.upgle.api.domain.user.dto.request.UserSignInRequest;
import com.upgle.api.domain.user.dto.request.UserSignUpRequest;
import com.upgle.api.domain.user.dto.response.UserSignUpResponse;
import com.upgle.api.exception.errors.AuthenticationFailException;
import com.upgle.api.exception.errors.DuplicateResourceException;
import com.upgle.api.exception.errors.NotFoundResourceException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final CacheService cacheService;
  private final JwtProvider jwtProvider;

  public User findById(Long id) {

    User user = userRepository.findById(id)
        .orElseThrow(() -> new NotFoundResourceException("user"));
    return user;
  }

  public User signUp(UserSignUpRequest request) {
    if (!("checked".equals(cacheService.getCacheString(request.getEmail())))) {
      throw new AuthenticationFailException("이메일 인증이 필요합니다");
    }

    if (userRepository.existsUserByNickname(request.getNickname())) {
      throw new DuplicateResourceException(request.getNickname());
    }
    String hashPassword = passwordEncoder.encode(request.getPassword());

    User newUser = request.toEntity(hashPassword);

    User savedUser = userRepository.save(newUser);

    cacheService.deleteCacheByStringKey(request.getEmail());
    cacheService.deleteCacheByStringKey((request.getEmail() + "code"));
    return savedUser;
  }

  public String SignIn(UserSignInRequest request) {
    User findUser = userRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new NotFoundResourceException("user"));

    if (!passwordCheck(request.getPassword(), findUser.getPassword())) {
      throw new AuthenticationFailException("패스워드가 일치하지 않습니다.");
    }

    return jwtProvider.createToke(findUser);
  }

  private boolean passwordCheck(String password, String encodedPassword) {
    return passwordEncoder.matches(password, encodedPassword);
  }
}
