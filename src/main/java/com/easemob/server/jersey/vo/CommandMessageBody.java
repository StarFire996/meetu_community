package com.easemob.server.jersey.vo;

import com.easemob.server.comm.MsgType;

public class CommandMessageBody extends MessageBody {
	private String action;

	public CommandMessageBody(String action) {
		super();
		this.action = action;
		this.getMsgBody().put("type", MsgType.CMD);
		this.getMsgBody().put("action", action);
	}

	public String getAction() {
		return action;
	}
}
