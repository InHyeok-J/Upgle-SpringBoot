package com.upgle.api.domain.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@DataJpaTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

  @Autowired
  UserRepository userRepository;

  private User saveUser;


  @BeforeEach
  public void setUp() throws Exception {
    final User user = User.builder().email("test@test.com").nickname("tester").password("testtest")
        .snsType("Local").build();
    userRepository.save(user);
  }

  @Test
  public void existsUserByNickName_존재하는경우_true() {
    Boolean existsUser = userRepository.existsUserByNickname("tester");
    assertThat(existsUser).isTrue();
  }

  @Test
  public void existsUserByNickName_존재하지않는경우_false() {
    Boolean existsUser = userRepository.existsUserByNickname("tester123");
    assertThat(existsUser).isFalse();
  }

  @Test
  public void existsUserByEmailAndSnsType_존재하는경우_true() {
    Boolean existsUser = userRepository.existsUserByEmailAndSnsType("test@test.com", "Local");
    assertThat(existsUser).isTrue();
  }

  @Test
  public void existsUserByEmailAndSnsTypee_존재하지않는경우_false() {
    Boolean existsUser = userRepository.existsUserByEmailAndSnsType("test12345@test.com", "Local");
    assertThat(existsUser).isFalse();
  }
}
