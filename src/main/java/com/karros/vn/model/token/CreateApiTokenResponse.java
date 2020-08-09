package com.karros.vn.model.token;

import com.karros.vn.model.MsgModel;

public class CreateApiTokenResponse {
  private String apiToken;
  
  private MsgModel msg;

  public String getApiToken() {
    return apiToken;
  }

  public void setApiToken(String apiToken) {
    this.apiToken = apiToken;
  }

  public MsgModel getMsg() {
    return msg;
  }

  public void setMsg(MsgModel msg) {
    this.msg = msg;
  }
}
