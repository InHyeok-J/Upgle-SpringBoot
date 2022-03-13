package com.upgle.api.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
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

    @Column(nullable = true)
    private Long snsId;

    @Column(nullable = true)
    private String snsType;

    @Builder
    public User(String email, String nickname, String password){
        this.email = email;
        this.nickname = nickname;
        this.password =  password;
    }
}
