package com.karros.vn.utils;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {
  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  @Value("${karros.app.jwtLoginTokenSecret}")
  private String jwtLoginTokenSecret;

  @Value("${karros.app.jwtApiTokenSecret}")
  private String jwtApiTokenSecret;

  @Value("${karros.app.jwtLoginTokenExpirationMs}")
  private int jwtLoginTokenExpirationMs;

  @Value("${karros.app.jwtApiTokenExpirationMs}")
  private int jwtApiTokenExpirationMs;

  public String generateJwtLoginToken(String info) {
    return generateJwtToken(info, jwtLoginTokenExpirationMs, jwtLoginTokenSecret);
  }

  public String generateJwtApiToken(String info) {
    return generateJwtToken(info, jwtApiTokenExpirationMs, jwtApiTokenSecret);
  }

  private String generateJwtToken(String info, int expirationMs, String tokenSecret) {

    return Jwts.builder()
        .setSubject(info)
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + expirationMs))
        .signWith(SignatureAlgorithm.HS512, tokenSecret)
        .compact();
  }

  public String getInfoFromJwtLoginToken(String token) {
    return getInfoFromJwtToken(token, jwtLoginTokenSecret);
  }

  public String getInfoFromJwtApiToken(String token) {
    return getInfoFromJwtToken(token, jwtApiTokenSecret);
  }
  private String getInfoFromJwtToken(String token, String tokenSecret) {
    return Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token).getBody().getSubject();
  }

  public int isJwtLoginTokenValid(String authToken) {
    return isJwtTokenValid(authToken, jwtLoginTokenSecret);
  }

  public int isJwtApiTokenValid(String authToken) {
    return isJwtTokenValid(authToken, jwtApiTokenSecret);
  }
  private int isJwtTokenValid(String authToken, String tokenSecret) {
    try {
      Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(authToken);
      return Const.Token.TOKEN_VALID;
    } catch (SignatureException e) {
      e.printStackTrace();
      logger.error("Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      e.printStackTrace();
      logger.error("JWT token is expired: {}", e.getMessage());
      return Const.Token.TOKEN_EXPIRED;
    } catch (UnsupportedJwtException e) {
      e.printStackTrace();
      logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }catch (Exception e) {
      e.printStackTrace();
    }

    return Const.Token.TOKEN_INVALID;
  }
}
