package com.upgle.api.domain.user;

import com.upgle.api.domain.cache.CacheService;
import com.upgle.api.domain.user.dto.request.UserSignUpRequest;
import com.upgle.api.domain.user.dto.response.UserSignUpResponse;
import com.upgle.api.exception.errors.AuthenticationFailException;
import com.upgle.api.exception.errors.DuplicateResourceException;
import com.upgle.api.exception.errors.NotFoundResourceException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final CacheService cacheService;

  public Long findById(Long id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new NotFoundResourceException("user"));
    return user.getId();
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
}
