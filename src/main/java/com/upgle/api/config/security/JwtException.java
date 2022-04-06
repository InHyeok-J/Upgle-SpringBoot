package com.upgle.api.config.security;

public class JwtException extends RuntimeException {

  public JwtException(String message) {
    super(message);
  }
}
