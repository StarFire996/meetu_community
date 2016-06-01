package com.easemob.server.api;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface IMUserAPI {

	/**
	 * 注册IM用户[单个] <br>
	 * POST
	 * 
	 * @param payload
	 *            <code>{"username":"${用户名}","password":"${密码}", "nickname":"${昵称值}"}</code>
	 * @return
	 */
	Object createNewIMUserSingle(Object payload);
	ObjectNode addFriendSingle(String ownerUserName, String friendUserName);
	ObjectNode deleteFriendSingle(String ownerUserName, String friendUserName);
	ObjectNode modifyIMUserPasswordWithAdminToken(String userName, ObjectNode dataObjectNode);
	ObjectNode imUserLogin(String ownerUserName, String password);
}
