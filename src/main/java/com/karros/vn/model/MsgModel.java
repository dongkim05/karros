package com.karros.vn.model;

import java.util.ArrayList;
import java.util.List;

public class MsgModel {
	private String title;
	private List<Msg> messages;
	
	public MsgModel() {
	}
	public MsgModel(Msg msg) {
		getMessages().add(msg);
	}
	public void addMsg(Msg msg) {
		getMessages().add(msg);
	}
	public boolean hasMsg() {
		return getMessages().size() > 0;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<Msg> getMessages() {
		if(messages == null) {
			messages = new ArrayList<>();
		}
		return messages;
	}
	public void setMessages(List<Msg> messages) {
		this.messages = messages;
	}
	
	
}
