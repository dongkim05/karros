package com.karros.vn.model.exception;

import com.karros.vn.model.MsgModel;

public class UnsignedException extends LogicException {

  /**
   * 
   */
  private static final long serialVersionUID = 4876684734506936014L;
  private MsgModel msgModel;

  public UnsignedException(MsgModel msgModel) {
    this.msgModel = msgModel;
  }

  @Override
  public MsgModel getMsgModel() {
    return msgModel;
  }

}
