package com.easemob.server.api;

import java.util.Map;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * This interface is created for RestAPI of Sending Message, it should be
 * synchronized with the API list.
 * 
 * @author Eric23 2016-01-05
 * @see http://docs.easemob.com/doku.php?id=start:100serverintegration:
 *      50messages
 */
public interface SendMessageAPI {
	/**
	 * 发送消息 <br>
	 * POST
	 * 
	 * @param targetType
	 *            消息发送目标类型, users | chatgroups | chatrooms
	 * @param targets
	 *            消息发送目标，数组形式，元素为目标标识
	 * @param msg
	 *            消息体
	 * @param from
	 *            发送者标识
	 * @param ext
	 *            扩展字段，可空
	 * @return
	 * @see com.easemob.server.jersey.vo.MessageBody
	 * @see com.easemob.server.jersey.vo.TextMessageBody
	 * @see com.easemob.server.jersey.vo.ImgMessageBody
	 * @see com.easemob.server.jersey.vo.AudioMessageBody
	 * @see com.easemob.server.jersey.vo.VideoMessageBody
	 * @see com.easemob.server.jersey.vo.CommandMessageBody
	 */
	ObjectNode sendMessages(String targetType, ArrayNode target, ObjectNode msg, String from, ObjectNode ext);
}
