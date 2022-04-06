package com.upgle.api.domain.user.dto.response;

import com.upgle.api.common.dto.BaseResponse;
import com.upgle.api.domain.user.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse extends BaseResponse {

  private Long id;

  private String email;

  private String nickname;

  private String profileImage;

  private String department;

  private ArrayList<String> wellTalent;

  private ArrayList<String> interestTalent;

  public UserResponse(String message, User user) {
    super(message);
    this.id = user.getId();
    this.email = user.getEmail();
    this.nickname = user.getNickname();
    this.profileImage = user.getProfileImage();
    this.department = user.getDepartment();
    this.wellTalent = talentToArray(user.getWellTalent());
    this.interestTalent = talentToArray(user.getInterestTalent());
  }

  private ArrayList<String> talentToArray(String talents) {
    if (talents == null) {
      return new ArrayList<String>();
    }
    List<String> list = Arrays.stream(talents.split(","))
        .map(String::new).toList();

    return new ArrayList<>(list);
  }
}
