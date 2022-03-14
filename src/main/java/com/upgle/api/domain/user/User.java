package com.upgle.api.domain.user;

import com.upgle.api.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(
        name = "snsUnique",
        columnNames = {"snsId", "snsType"}
    ),
    @UniqueConstraint(
        name = "userNicknameKey",
        columnNames = "nickname"
    )
})
public class User extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false, name = "nickname")
  private String nickname;

  @Column(nullable = true)
  private String password;

  @Column(nullable = true)
  private String profileImage;

  @Column(nullable = true, length = 500)
  private String introduction;

  @Column(nullable = true)
  private String department;

  @Column(nullable = true)
  private String wellTalent;

  @Column(nullable = true)
  private String interestTalent;

  @Column(nullable = true, name = "snsId")
  private Long snsId;

  @Column(nullable = true, name = "snsType")
  private String snsType;

  @Builder
  public User(String email, String nickname, String password) {
    this.email = email;
    this.nickname = nickname;
    this.password = password;
  }
}
