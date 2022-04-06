package com.upgle.api.domain.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  boolean existsUserByNickname(String nickname);

  boolean existsUserByEmailAndSnsType(String email, String snsType);

  Optional<User> findByEmail(String email);
}
