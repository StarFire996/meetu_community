package com.easemob.server.api;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface ChatGroupsAPI {
	
	ObjectNode creatChatGroups(ObjectNode dataObjectNode);
	ObjectNode deleteChatGroups(String chatgroupid);
}
