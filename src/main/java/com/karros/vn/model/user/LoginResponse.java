package com.karros.vn.model.user;

import com.karros.vn.model.MsgModel;

public class LoginResponse {
  private String loginToken;

  private String apiToken;

  private MsgModel msgModel;

  public MsgModel getMsgModel() {
    return msgModel;
  }
  public void setMsgModel(MsgModel msgModel) {
    this.msgModel = msgModel;
  }
  public String getLoginToken() {
    return loginToken;
  }
  public void setLoginToken(String loginToken) {
    this.loginToken = loginToken;
  }
  public String getApiToken() {
    return apiToken;
  }
  public void setApiToken(String apiToken) {
    this.apiToken = apiToken;
  }
}
