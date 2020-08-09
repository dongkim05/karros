package com.karros.vn.model.exception;

import com.karros.vn.model.MsgModel;

public class InvalidTokenException extends LogicException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1343223L;
	private MsgModel msgModel;
	
	public InvalidTokenException(MsgModel msgModel) {
		this.msgModel = msgModel;
	}
	
	@Override
	public MsgModel getMsgModel() {
		return msgModel;
	}

}
