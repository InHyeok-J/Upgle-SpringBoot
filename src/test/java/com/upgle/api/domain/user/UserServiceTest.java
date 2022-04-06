package com.upgle.api.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.will;
import static org.mockito.Mockito.doReturn;

import com.upgle.api.domain.cache.CacheService;
import com.upgle.api.domain.user.dto.request.UserSignUpRequest;
import com.upgle.api.domain.user.dto.response.UserSignUpResponse;
import com.upgle.api.exception.errors.AuthenticationFailException;
import com.upgle.api.exception.errors.DuplicateResourceException;
import com.upgle.api.exception.errors.NotFoundResourceException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @Mock
  UserRepository userRepository;

  @Spy // 실제 메소드대로 동작한다.
  BCryptPasswordEncoder passwordEncoder;

  @Mock
  CacheService cacheService;

  @InjectMocks
  UserService userService;


  @DisplayName("사용자 조회 성공")
  @Test
  void findOne() {
    //given
    doReturn(stubUserOne()).when(userRepository).findById(1L);
    //when
    Long result = userService.findById(1L);
    //then
    assertThat(result).isEqualTo(1L);
  }

  @DisplayName("없는 사용자, 조회실패")
  @Test
  void findOne_fail() {
    //given
    Long inputValue = 0L;
    doReturn(Optional.ofNullable(null)).when(userRepository).findById(inputValue);
    //when && then
    NotFoundResourceException exception = assertThrows(NotFoundResourceException.class,
        () -> userService.findById(inputValue));

    assertEquals("user[찾을 수 없습니다.]", exception.getMessage());
  }

  @DisplayName("회원가입_이메일미인증으로_실패")
  @Test
  void signUp_이메일_미인증() {
    //given
    UserSignUpRequest dto = createSignUpRequestDto();
    doReturn("pre:checked").when(cacheService).getCacheString(dto.getEmail());

    //when
    AuthenticationFailException exception = assertThrows(AuthenticationFailException.class,
        () -> userService.signUp(dto));

    //then
    assertThat("이메일 인증이 필요합니다[인증 실패]").isEqualTo(exception.getMessage());
  }

  @DisplayName("회원가입_중복닉네임으로_실패")
  @Test
  void signUp_중복닉네임_실패() {
    //given
    UserSignUpRequest dto = createSignUpRequestDto();
    doReturn("checked").when(cacheService).getCacheString(dto.getEmail());
    doReturn(true).when(userRepository).existsUserByNickname(dto.getNickname());

    //when
    DuplicateResourceException exception = assertThrows(DuplicateResourceException.class,
        () -> userService.signUp(dto));
    //then
    assertEquals(dto.getNickname() + "[중복]", exception.getMessage());
  }

  @DisplayName("회원가입_성공시_User리턴")
  @Test
  void signUp_성공() {
    //given
    UserSignUpRequest dto = createSignUpRequestDto();
    String hashPassword = passwordEncoder.encode(dto.getPassword());
    User savedUser = saveUserStub(dto, hashPassword);
    given(cacheService.getCacheString(dto.getEmail())).willReturn("checked");
    given(userRepository.existsUserByNickname(dto.getNickname())).willReturn(false);
    given(userRepository.save(any(User.class))).willReturn(savedUser);
    //when
    User response = userService.signUp(dto);

    //then
    assertEquals(response.getEmail(), dto.getEmail());
    assertEquals(response.getId(), 2L);
    assertEquals(response.getNickname(), dto.getNickname());
  }


  private Optional<User> stubUserOne() {
    User user = User.builder()
        .email("email@email.com")
        .nickname("nickname")
        .password("hashedPassword")
        .snsType("Local")
        .build();
    ReflectionTestUtils.setField(user, "id", 1L);
    return Optional.of(user);
  }

  private User saveUserStub(UserSignUpRequest dto, String hashedPassword) {
    User newUser = dto.toEntity(hashedPassword);
    ReflectionTestUtils.setField(newUser, "id", 2L);
    return newUser;
  }

  private UserSignUpRequest createSignUpRequestDto() {
    return new UserSignUpRequest("email@email.com", "nickname", "password");
  }

}
