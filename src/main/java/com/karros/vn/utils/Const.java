package com.karros.vn.utils;

public interface Const {
  interface User{
    String USER_KEY = "user";
  }

  interface Token{
    int TOKEN_VALID = 1;
    int TOKEN_EXPIRED = 2;
    int TOKEN_INVALID = -1;
    String LOGIN_TOKEN_HEADER = "LOGIN_TOKEN_HEADER";
    String API_TOKEN_HEADER = "API_TOKEN_HEADER";
  }
  
  interface Text {
    int MAX_TEXT_100 = 100;
    int MAX_TEXT_1000 = 1000;
  }
}
