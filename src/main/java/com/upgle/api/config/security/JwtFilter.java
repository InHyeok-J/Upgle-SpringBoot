package com.upgle.api.config.security;

import com.upgle.api.config.jwt.JwtProvider;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {

  private static final String AUTHORIZATION_HEADER = "Authorization";
  private static final String HEADER_PREFIX = "Bearer ";
  private final JwtProvider jwtProvider;
  private final OrRequestMatcher orRequestMatcher;

  public JwtFilter(JwtProvider jwtProvider, List<AntPathRequestMatcher> skipPathList) {
    this.jwtProvider = jwtProvider;
    this.orRequestMatcher = new OrRequestMatcher(new ArrayList<>(skipPathList));
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    if (orRequestMatcher.matches(request)) {
      filterChain.doFilter(request, response);
      return;
    }

    String jwtToken = extractToken(request);

    if (jwtProvider.validateToken(jwtToken)) {
      UserDetails userInfo = jwtProvider.getUserDetail(jwtToken);
      Authentication authentication = new UsernamePasswordAuthenticationToken(
          userInfo.getUsername(), userInfo.getPassword(), userInfo.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
      log.info("Securit Context에 인증정보 저장완료");
    } else {
      log.info("Jwt Validation 실패");
    }

    filterChain.doFilter(request, response);
  }

  private String extractToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(HEADER_PREFIX)) {
      return bearerToken.substring(HEADER_PREFIX.length());
    } else {
      throw new JwtException("Header에 Token이 없습니다.");
    }
  }
}
