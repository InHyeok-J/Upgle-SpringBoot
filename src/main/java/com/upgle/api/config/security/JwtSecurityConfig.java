package com.upgle.api.config.security;

import com.upgle.api.config.jwt.JwtProvider;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
public class JwtSecurityConfig extends
    SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  private final JwtProvider jwtProvider;

  @Override
  public void configure(HttpSecurity http) {
    List<AntPathRequestMatcher> skip = new ArrayList<>();

    skip.add(new AntPathRequestMatcher("/", HttpMethod.GET.name()));
    skip.add(new AntPathRequestMatcher("/api/user", HttpMethod.POST.name()));
    skip.add(new AntPathRequestMatcher("/api/user/signin", HttpMethod.POST.name()));
    skip.add(new AntPathRequestMatcher("/api/email"));

    JwtFilter customJwtFilter = new JwtFilter(jwtProvider, skip);
    JwtExceptionFilter jwtExceptionFilter = new JwtExceptionFilter();

    http.addFilterBefore(customJwtFilter, UsernamePasswordAuthenticationFilter.class);
    http.addFilterBefore(jwtExceptionFilter, JwtFilter.class);
  }
}
