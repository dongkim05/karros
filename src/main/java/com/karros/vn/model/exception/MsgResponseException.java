package com.karros.vn.model.exception;

import com.karros.vn.model.MsgModel;

public class MsgResponseException extends LogicException {

  /**
   * 
   */
  private static final long serialVersionUID = 1273975494495973190L;
  private MsgModel msgModel;

  public MsgResponseException(MsgModel msgModel) {
    this.msgModel = msgModel;
  }

  @Override
  public MsgModel getMsgModel() {
    return msgModel;
  }
}
