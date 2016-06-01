package com.easemob.server.jersey;

import org.glassfish.jersey.client.JerseyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easemob.server.api.ChatGroupsAPI;
import com.easemob.server.comm.HTTPMethod;
import com.easemob.server.comm.Roles;
import com.easemob.server.jersey.EasemobChatGroups;
import com.easemob.server.jersey.utils.JerseyUtils;
import com.easemob.server.jersey.vo.ClientSecretCredential;
import com.easemob.server.jersey.vo.Credential;
import com.easemob.server.jersey.vo.EndPoints;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.meetu.config.Constants;

public class EasemobChatGroups implements ChatGroupsAPI{

	private static final Logger LOGGER = LoggerFactory.getLogger(EasemobChatGroups.class);
    private static final JsonNodeFactory factory = new JsonNodeFactory(false);
    private static final String APPKEY = Constants.APPKEY;
    
 // 通过app的client_id和client_secret来获取app管理员token
    private static Credential credential = new ClientSecretCredential(Constants.APP_CLIENT_ID,
            Constants.APP_CLIENT_SECRET, Roles.USER_ROLE_APPADMIN);
	
    
    public static void main(String[] args){
    	/** 创建群组 
		 * curl示例
		 * curl -X POST 'https://a1.easemob.com/easemob-playground/test1/chatgroups' -H 'Authorization: Bearer {token}' -d '{"groupname":"测试群组","desc":"测试群组","public":true,"approval":true,"owner":"xiaojianguo001","maxusers":333,"members":["xiaojianguo002","xiaojianguo003"]}'
		 
		ObjectNode dataObjectNode = JsonNodeFactory.instance.objectNode();
		dataObjectNode.put("groupname", "测试群组");
		dataObjectNode.put("desc", "测试群组");
		dataObjectNode.put("approval", true);
		dataObjectNode.put("public", true);
		dataObjectNode.put("maxusers", 2);
		dataObjectNode.put("owner", "10032");
		ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode();
		arrayNode.add("10033");
		dataObjectNode.put("members", arrayNode);
		ObjectNode creatChatGroupNode = creatChatGroups(dataObjectNode);
		System.out.println(creatChatGroupNode.toString());
		*/
    }
    

    /**
     * @return
     * {
     * 		"action":"post",
     * 		"application":"d3c19a00-dc6b-11e5-8a09-739ef66c9c2c",
     * 		"uri":"https://a1.easemob.com/imeetu/meetu",
     * 		"entities":[],"data":{"groupid":"174551488218005952"},
     * 		"timestamp":1458205735359,
     * 		"duration":66,
     * 		"organization":"imeetu",
     * 		"applicationName":"meetu",
     * 		"statusCode":200
     * }
     * 
     * */
    @Override
    public ObjectNode creatChatGroups(ObjectNode dataObjectNode) {
		// TODO Auto-generated method stub
		ObjectNode objectNode = factory.objectNode();

		// check appKey format
		if (!JerseyUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);

			objectNode.put("message", "Bad format of Appkey");

			return objectNode;
		}

		// check properties that must be provided
		if (!dataObjectNode.has("groupname")) {
			LOGGER.error("Property that named groupname must be provided .");

			objectNode.put("message", "Property that named groupname must be provided .");

			return objectNode;
		}
		if (!dataObjectNode.has("desc")) {
			LOGGER.error("Property that named desc must be provided .");

			objectNode.put("message", "Property that named desc must be provided .");

			return objectNode;
		}
		if (!dataObjectNode.has("public")) {
			LOGGER.error("Property that named public must be provided .");

			objectNode.put("message", "Property that named public must be provided .");

			return objectNode;
		}
		if (!dataObjectNode.has("approval")) {
			LOGGER.error("Property that named approval must be provided .");

			objectNode.put("message", "Property that named approval must be provided .");

			return objectNode;
		}
		if (!dataObjectNode.has("owner")) {
			LOGGER.error("Property that named owner must be provided .");

			objectNode.put("message", "Property that named owner must be provided .");

			return objectNode;
		}
		if (!dataObjectNode.has("members") || !dataObjectNode.path("members").isArray()) {
			LOGGER.error("Property that named members must be provided .");

			objectNode.put("message", "Property that named members must be provided .");

			return objectNode;
		}

		try {

			JerseyWebTarget webTarget = EndPoints.CHATGROUPS_TARGET.resolveTemplate("org_name", APPKEY.split("#")[0])
					.resolveTemplate("app_name", APPKEY.split("#")[1]);

			objectNode = JerseyUtils.sendRequest(webTarget, dataObjectNode, credential, HTTPMethod.METHOD_POST, null);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}
    
    /**
	 * 删除群组
	 * 
	 */
    @Override
	public ObjectNode deleteChatGroups(String chatgroupid) {
		ObjectNode objectNode = factory.objectNode();

		// check appKey format
		if (!JerseyUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);

			objectNode.put("message", "Bad format of Appkey");

			return objectNode;
		}

		try {

			JerseyWebTarget webTarget = EndPoints.CHATGROUPS_TARGET.resolveTemplate("org_name", APPKEY.split("#")[0])
					.resolveTemplate("app_name", APPKEY.split("#")[1]).path(chatgroupid);

			objectNode = JerseyUtils.sendRequest(webTarget, null, credential, HTTPMethod.METHOD_DELETE, null);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}

}
