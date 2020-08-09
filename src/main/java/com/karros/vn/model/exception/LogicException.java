package com.karros.vn.model.exception;

import com.karros.vn.model.MsgModel;

public abstract class LogicException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = 1453536555643L;

  public abstract MsgModel getMsgModel();
}
