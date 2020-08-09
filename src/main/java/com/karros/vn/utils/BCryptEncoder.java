package com.karros.vn.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class BCryptEncoder extends BCryptPasswordEncoder {

  /** Token prefix */
  private final String tokenPrefix = "$2a$04$";

  /**
   * Generate password. Remove token prefix because it contain bcrypt info.
   *
   * @param plainText password plain text
   * @return encoded password
   */
  public String generatePassword(String plainText) {
    String token = super.encode(plainText);
    return token.substring(tokenPrefix.length());
  }

  /**
   * Check that a plain text token matches a previously hashed one. Add token prefix because it was removed when
   * generate token.
   *
   * @param plainText the plain text password to verify
   * @param token the previously-hashed password
   * @return true if the token match, false otherwise
   */
  @Override
  public boolean matches(CharSequence plainText, String token) {
    return super.matches(plainText, tokenPrefix + token);
  }

}
