package com.karros.vn.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.karros.vn.model.Msg;
import com.karros.vn.model.MsgModel;
import com.karros.vn.model.exception.InvalidTokenException;
import com.karros.vn.model.exception.MsgResponseException;
import com.karros.vn.model.exception.UnsignedException;

@RestControllerAdvice
public class AppExceptionController {
	
	@ExceptionHandler(UnsignedException.class)
	public ResponseEntity<MsgModel> unsignedException(final UnsignedException e) {
		return new ResponseEntity<>(e.getMsgModel(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MsgResponseException.class)
	public ResponseEntity<MsgModel> msgResponseException(final MsgResponseException e) {
		return new ResponseEntity<>(e.getMsgModel(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<MsgModel> msgResponseException(final InvalidTokenException e) {
		return new ResponseEntity<>(e.getMsgModel(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<MsgModel> internalError(final Exception e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String exceptionAsString = sw.toString();
		e.printStackTrace();
		MsgModel msg = new MsgModel();
		msg.addMsg(new Msg("0007", exceptionAsString));
		return new ResponseEntity<>(msg,HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
