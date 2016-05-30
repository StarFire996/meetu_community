package com.meetu.config;

import com.baidu.yun.push.auth.PushKeyPair;
import com.meetu.util.PropertiesUtils;


public class Constants {

/**
     * Redis中Key的前缀
     */
    public final static String REDIS_KEY_PREFIX = "AUTH_KEY_";

    /**
     * Redis中Token的前缀
     */
    public final static String REDIS_TOKEN_PREFIX = "AUTH_TOKEN_";
    public final static String redis_online = "redis_online";
    public final static String redis_online_channelId = "channelId_";
    public final static String redis_online_deviceType = "deviceType_";
    public final static String redis_online_deviceCode = "deviceCode_";
    public final static String redis_online_latestCode = "latestCode_";
    
    public final static String redis_oss = "redis_oss_token";
    
    //数据库自定义参数redis用hash（哈希表）存储，主键：
    public final static String paramsKey = "settingParams";
    public final static String timeInterval = "timeInterval";
    public final static String timeIntervalT = "timeIntervalT";//接收biu的时间间隔
    public final static String biuExpire = "biuExpire";
    public final static String sendBiuNumbers = "sendBiuNumbers";
    public final static String sendRewards = "sendRewards";
    public final static String grabRewards = "grabRewards";
    public final static String biubiuListNumbers = "biubiuListNumbers";
    public final static String param_init = "param_init";
    public final static String param_pers = "param_pers";
    public final static String param_inte = "param_inte";
    public final static String param_pers_num = "param_pers_num";
    public final static String param_inte_num = "param_inte_num";
    public final static String acceptBiuCost = "acceptBiuCost";//接受超出1个抢biu用户,要消耗的biu币
    public final static String targetBiuNumbers = "targetBiuNumbers";//匹配用户默认数量
    public final static String biu_time_interval = "biu_time_interval";//biu的弹出最大时间间隔
    public final static String biu_time_interval_min = "biu_time_interval_min";//biu的弹出最小时间间隔
    public final static String biuRewards = "biuRewards";//发biu扣除biu币基数
    public final static String stepNumbers = "stepNumbers";//从第几个阶梯开始扣biu币
    public final static String stepValue = "stepValue";//阶梯值
    public final static String rewardsNumbers = "rewardsNumbers";//一次性奖励biu币数量
    
    
    
    public final static Integer rewardsNumbers_default = 5;
    public final static Integer biuRewards_default = 1;
    public final static Integer stepNumbers_default = 1;
    public final static Integer stepValue_default = 10;
    public final static Integer biu_time_interval_default = 10;
    public final static Integer biu_time_interval_min_default = 5;
    public final static Integer targetBiuNumbers_default = 20;
    public final static Integer acceptBiuCost_default = 2;
    public final static Integer timeInterval_default = 3600;
    public final static Integer sendBiuNumbers_default = 50;
    public final static Integer timeIntervalT_default = 5;
    public final static Integer biuExpire_default = 180;
    public final static Integer sendRewards_default = 5;
    public final static Integer grabRewards_default = 5;
    public final static Integer biubiuListNumbers_default = 25;
    public final static Integer param_init_default = 70;
    public final static Integer param_pers_default = 20;
    public final static Integer param_inte_default = 10;
    public final static Integer param_pers_num_default = 5;
    public final static Integer param_inte_num_default = 10;
    
    
    // API_HTTP_SCHEMA
 	public static String API_HTTP_SCHEMA = "https";
 	// API_SERVER_HOST
 	public static String API_SERVER_HOST = PropertiesUtils.getProperties().getProperty("API_SERVER_HOST");
 	// APPKEY
 	public static String APPKEY = PropertiesUtils.getProperties().getProperty("APPKEY");
 	// APP_CLIENT_ID
 	public static String APP_CLIENT_ID = PropertiesUtils.getProperties().getProperty("APP_CLIENT_ID");
 	// APP_CLIENT_SECRET
 	public static String APP_CLIENT_SECRET = PropertiesUtils.getProperties().getProperty("APP_CLIENT_SECRET");
 	// DEFAULT_PASSWORD
 	public static String DEFAULT_PASSWORD = "123456";
 	
	//目前只有"cn-hangzhou"这个region可用, 不要使用填写其他region的值
 	public static String REGION_CN = PropertiesUtils.getProperties().getProperty("REGION_CN");
 	//当前 STS API 版本
 	public static String STS_API_VERSION = PropertiesUtils.getProperties().getProperty("STS_API_VERSION");
 	public static String accessKeyId = PropertiesUtils.getProperties().getProperty("accessKeyId");
 	public static String accessKeySecret = PropertiesUtils.getProperties().getProperty("accessKeySecret");
 	public static String roleArn = PropertiesUtils.getProperties().getProperty("roleArn");
 	public static String bucketName = PropertiesUtils.getProperties().getProperty("bucketName");
 	public static String endpoint = PropertiesUtils.getProperties().getProperty("endpoint");
 	//圆形头像图片规则
 	public static String rule_circle = PropertiesUtils.getProperties().getProperty("ruleCircle");
 	//方形缩略图规则
 	public static String rule_thumbnail = PropertiesUtils.getProperties().getProperty("ruleThumbnail");
 	//OSS服务地址
 	public static String serviceOSS = PropertiesUtils.getProperties().getProperty("serviceOSS");
 	//IMG服务地址
 	public static String serviceIMG = PropertiesUtils.getProperties().getProperty("serviceIMG");
 	
 	
 	public static final String android_apiKey = PropertiesUtils.getProperties().getProperty("baidu.android.apiKey");
 	public static final String android_secretKey = PropertiesUtils.getProperties().getProperty("baidu.android.secretKey");
 	public static final String ios_apiKey = PropertiesUtils.getProperties().getProperty("baidu.ios.apiKey");
 	public static final String ios_secretKey = PropertiesUtils.getProperties().getProperty("baidu.ios.secretKey");
 	
 	
 	public static final PushKeyPair androidPair = new PushKeyPair(android_apiKey, android_secretKey);
 	public static final PushKeyPair iosPair = new PushKeyPair(ios_apiKey, ios_secretKey);
 	
 	
 	
 	//Redis
 	public static final String redisHost = PropertiesUtils.getProperties().getProperty("redis.host");
 	public static final String redisPassword = PropertiesUtils.getProperties().getProperty("redis.password");
 	public static final String redisPort = PropertiesUtils.getProperties().getProperty("redis.port");
 	public static final String redisTimeout = PropertiesUtils.getProperties().getProperty("redis.timeout");
 	
 	//推送服务器地址
 	public static final String push_service_url = PropertiesUtils.getProperties().getProperty("push_service_url");

}
