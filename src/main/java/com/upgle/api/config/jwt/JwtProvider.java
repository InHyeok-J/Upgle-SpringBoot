package com.upgle.api.config.jwt;

import com.upgle.api.domain.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtProvider {

  private static final String AUTHORITIES_KEY = "auth";
  private final Long ACCESS_TOKE_EXPIRED_TIME_SECOND;
  private final Key key;

  public JwtProvider(JwtConfigProperty jwtConfigProperty) {
    byte[] keyByte = Decoders.BASE64.decode(jwtConfigProperty.getSecret());

    this.ACCESS_TOKE_EXPIRED_TIME_SECOND = jwtConfigProperty.getAccess_token_expired_time();
    this.key = Keys.hmacShaKeyFor(keyByte);
  }

  public String createToke(User userEntity) {
    long now = (new Date()).getTime();
    Date accessTokenExpiredIn = new Date(now + ACCESS_TOKE_EXPIRED_TIME_SECOND * 1000);
    String accessToken = Jwts.builder()
        .setSubject(userEntity.getId().toString())
        .setExpiration(accessTokenExpiredIn)
        .claim(AUTHORITIES_KEY, "USER")
        .signWith(key)
        .compact();

    return accessToken;
  }

  public UserDetails getUserDetail(String token) {
    Claims claims = Jwts
        .parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();

    SimpleGrantedAuthority auth = new SimpleGrantedAuthority(
        claims.get(AUTHORITIES_KEY).toString());
    Collection<? extends GrantedAuthority> authorities = new ArrayList<>(List.of(auth));

    return new org.springframework.security.core.userdetails.User(claims.getSubject(), "",
        authorities);
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
      log.info("잘못된 JWT 서명입니다.");
      return false;
    } catch (ExpiredJwtException e) {
      log.info("만료된 JWT 토큰입니다.");
      return false;
    } catch (UnsupportedJwtException e) {
      log.info("지원되지 않는 JWT 토큰입니다.");
      return false;
    } catch (IllegalArgumentException e) {
      log.info("JWT 토큰이 잘못되었습니다.");
      return false;
    }
  }
}
