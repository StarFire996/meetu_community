package com.meetu.util;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.meetu.config.Constants;

/**
 * 阿里云服务
 * 
 * TOKEN过期时间min/max:15min/1hour
 * */
public class StsService {

	public static final String REGION_CN = Constants.REGION_CN;
	public static final String STS_API_VERSION = Constants.STS_API_VERSION;
	
	
	static AssumeRoleResponse assumeRole(String accessKeyId, String accessKeySecret,
            String roleArn, String roleSessionName, String policy,
            ProtocolType protocolType) throws ClientException {
		
		try {
			// 创建一个 Aliyun Acs Client, 用于发起 OpenAPI 请求
			IClientProfile profile = DefaultProfile.getProfile(REGION_CN, accessKeyId, accessKeySecret);
			DefaultAcsClient client = new DefaultAcsClient(profile);

			// 创建一个 AssumeRoleRequest 并设置请求参数
			final AssumeRoleRequest request = new AssumeRoleRequest();
			request.setVersion(STS_API_VERSION);
			request.setMethod(MethodType.POST);
			request.setProtocol(protocolType);

			request.setRoleArn(roleArn);
			request.setRoleSessionName(roleSessionName);
			request.setPolicy(policy);

			request.setDurationSeconds((long)3000);
			// 发起请求，并得到response
			final AssumeRoleResponse response = client.getAcsResponse(request);

			return response;
		} catch (ClientException e) {
			throw e;
		}
	}
	public static JSONObject getAccess() {
		JSONObject json = new JSONObject();
		// 只有 RAM用户（子账号）才能调用 AssumeRole 接口
	    // 阿里云主账号的AccessKeys不能用于发起AssumeRole请求
	    // 请首先在RAM控制台创建一个RAM用户，并为这个用户创建AccessKeys
	    String accessKeyId = Constants.accessKeyId;
	    String accessKeySecret = Constants.accessKeySecret;

	    // AssumeRole API 请求参数: RoleArn, RoleSessionName, Polciy, and DurationSeconds

	    // RoleArn 需要在 RAM 控制台上获取
	    String roleArn = Constants.roleArn;

	    // RoleSessionName 是临时Token的会话名称，自己指定用于标识你的用户，主要用于审计，或者用于区分Token颁发给谁
	    // 但是注意RoleSessionName的长度和规则，不要有空格，只能有'-' '_' 字母和数字等字符
	    // 具体规则请参考API文档中的格式要求
	    String roleSessionName = "alice-001";

	    // 如何定制你的policy?
	    String policy = "{\n" +
	            "    \"Version\": \"1\", \n" +
	            "    \"Statement\": [\n" +
	            "        {\n" +
	            "            \"Action\": [\n" +
	            "                \"oss:GetBucket\", \n" +
	            "                \"oss:GetObject\", \n" +
	            "				 \"oss:DeleteObject\", \n" +
	            "				 \"oss:ListParts\", \n"+
	            "				 \"oss:AbortMultipartUpload\", \n"+
	            "		   		 \"oss:PutObject\" \n"+
	            "            ], \n" +
	            "            \"Resource\": [\n" +
	            "                \"acs:oss:*:*:*\"\n" +
	            "            ], \n" +
	            "            \"Effect\": \"Allow\"\n" +
	            "        }\n" +
	            "    ]\n" +
	            "}";


	    // 此处必须为 HTTPS
	    ProtocolType protocolType = ProtocolType.HTTPS;

	    try {
	    	final AssumeRoleResponse response = assumeRole(accessKeyId, accessKeySecret,
	              roleArn, roleSessionName, policy, protocolType);

//	    	System.out.println("Expiration: " + response.getCredentials().getExpiration());
//	    	System.out.println("Access Key Id: " + response.getCredentials().getAccessKeyId());
//	    	System.out.println("Access Key Secret: " + response.getCredentials().getAccessKeySecret());
//	    	System.out.println("Security Token: " + response.getCredentials().getSecurityToken());
	    	json.put("expiration", response.getCredentials().getExpiration());
	    	json.put("accessKeyId", response.getCredentials().getAccessKeyId());
	    	json.put("accessKeySecret", response.getCredentials().getAccessKeySecret());
	    	json.put("securityToken", response.getCredentials().getSecurityToken());
	    	return json;
	    } catch (ClientException e) {
	    	System.out.println("Failed to get a token.");
	    	System.out.println("Error code: " + e.getErrCode());
	    	System.out.println("Error message: " + e.getErrMsg());
	    }
	    return null;
	  }
	
	public static void main(String[] args){
		
//		getAccess();
//		System.out.println(generUrl("3.pic.jpg"));
//		delphoto("800.jpg");
		
	}
	public static OSSClient getOSSClient(){
		
		JSONObject access = getAccess();
		if(access!=null){
			String accessKeyId = access.getString("accessKeyId");
			String accessKeySecret = access.getString("accessKeySecret");
			String securityToken = access.getString("securityToken");
			//https://oss-cn-beijing.aliyuncs.com
			//https://sts.aliyuncs.com
			String endpoint = Constants.endpoint;
			OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret, securityToken);
			return client;
		}else{
			return null;
			
		}
	}
//	public static String generUrl(String objectKey){
//		
//		String bucketName = Constants.bucketName;
//		// 设置URL过期时间为1小时
//		Date expiration = new Date(new Date().getTime() + 3600l*1000*24*365*100);
//		
//		OSSClient ossclient = getOSSClient();
//		
////		OSSObject  ossobject = ossclient.getObject(bucketName, objectKey+"@100w_100h.jpg");
//		
//		if(ossclient!=null){
//			URL url = ossclient.generatePresignedUrl(bucketName, objectKey, expiration);
//			
//			return url.toString();
//		}
//		return null;
//	}
	/**
	 * 原图url
	 * */
	public static String generateUrl(String objectKey){
		return "http://"+Constants.bucketName+"."+Constants.serviceOSS+"/"+objectKey;
	}
	/**
	 * 圆形缩略图url，头像
	 * */
	public static String generateCircleUrl(String objectKey){
		return "http://"+Constants.bucketName+"."+Constants.serviceIMG+"/"+objectKey+Constants.rule_circle;
	}
	/**
	 * 方形缩略图url，照片墙
	 * */
	public static String generateThumbnailUrl(String objectKey){
		return "http://"+Constants.bucketName+"."+Constants.serviceIMG+"/"+objectKey+Constants.rule_thumbnail;
	}
	
	
	public static void delphoto(String key){
		getOSSClient().deleteObject(Constants.bucketName, key);
	}
	
	
}
