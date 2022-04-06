package com.upgle.api.common.dto;

import java.util.Collection;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public class AuthUser {

  private Long id;
  private Collection<? extends GrantedAuthority> authority;

  public AuthUser(Long id, Collection<? extends GrantedAuthority> authority) {
    this.id = id;
    this.authority = authority;
  }
  
}
