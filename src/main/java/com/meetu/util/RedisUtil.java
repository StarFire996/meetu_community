package com.meetu.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.alibaba.fastjson.JSONObject;
import com.meetu.community.domain.User;
import com.meetu.config.Constants;

public class RedisUtil {

	protected static Logger LOGGER = LoggerFactory.getLogger(RedisUtil.class);

	@Resource
	public Properties systemConstant;

	// Redis服务器IP
	private static String ADDR_ARRAY = Constants.redisHost;

	// Redis的端口号
	private static int PORT = Integer.parseInt(Constants.redisPort);

	// 可用连接实例的最大数目，默认值为8；
	// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
	private static int MAX_ACTIVE = -1;

	// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	private static int MAX_IDLE = 8;

	// 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
	private static int MAX_WAIT = 10000;

	// 超时时间
	private static int TIMEOUT = 30000;

	// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	private static boolean TEST_ON_BORROW = true;

	private static JedisPool jedisPool = null;

	/**
	 * redis过期时间,以秒为单位
	 */
	public final static int EXRP_HOUR = 60 * 60; // 一小时

	public final static int EXRP_DAY = 60 * 60 * 24; // 一天

	public final static int EXRP_WEEK = 60 * 60 * 24 * 7;

	public final static int EXRP_MONTH = 60 * 60 * 24 * 30; // 一个月

	/**
	 * 初始化Redis连接池
	 */
	private static void initialPool() {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(MAX_ACTIVE);
			config.setMaxIdle(MAX_IDLE);
			config.setMaxWaitMillis(MAX_WAIT);
			config.setTestOnBorrow(TEST_ON_BORROW);
			config.setTestWhileIdle(true);
			config.setMinEvictableIdleTimeMillis(60000);
			config.setTimeBetweenEvictionRunsMillis(30000);
			jedisPool = new JedisPool(config, ADDR_ARRAY.split(",")[0], PORT,
					TIMEOUT, Constants.redisPassword);
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("First create JedisPool error : " + e);
			}
			try {
				// 如果第一个IP异常，则访问第二个IP
				JedisPoolConfig config = new JedisPoolConfig();
				config.setMaxTotal(MAX_ACTIVE);
				config.setMaxIdle(MAX_IDLE);
				config.setMaxWaitMillis(MAX_WAIT);
				config.setTestOnBorrow(TEST_ON_BORROW);
				jedisPool = new JedisPool(config, ADDR_ARRAY.split(",")[1],
						PORT, TIMEOUT, Constants.redisPassword);
			} catch (Exception e2) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error("Second create JedisPool error : " + e2);
				}
			}
		}
	}

	/**
	 * 在多线程环境同步初始化
	 */
	private static synchronized void poolInit() {
		if (jedisPool == null) {
			initialPool();
		}
	}

	/**
	 * 同步获取Jedis实例
	 * 
	 * @return Jedis
	 */
	public synchronized static Jedis getJedis() {
		if (jedisPool == null) {
			poolInit();
			// System.out.println(new Date().toString() +
			// "______jedisPool_____Init__________");
		}
		Jedis jedis = null;
		try {
			if (jedisPool != null) {
				jedis = jedisPool.getResource();
			}
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Get jedis error : " + e);
			}
		} finally {
			// returnResource(jedis);
		}
		return jedis;
	}

	public synchronized static String generateToken() {
		long current = System.currentTimeMillis();
		String now = new Long(current).toString();
		return Common.encryptByMD5(now);
	}

	/**
	 * 释放jedis资源
	 * 
	 * @param jedis
	 */
	public static void returnResource(final Jedis jedis) {
		if (jedis != null && jedisPool != null) {
			jedis.close();
		}
	}

	/**
	 * 设置 String
	 * 
	 * @param key
	 * @param value
	 * @author lzming
	 */
	public static String setString(String key, String value) {
		Jedis jedis = getJedis();
		try {
			value = value == null ? "" : value;
			return jedis.set(key, value);
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Set key error : " + e);
			}
		} finally {
			returnResource(jedis);
		}
		return null;
	}

	/**
	 * 删除 String
	 * 
	 * @param key
	 * @author lzming 0308
	 */
	public static Long delString(String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.del(key);
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Set key error : " + e);
			}
		} finally {
			returnResource(jedis);
		}
		return null;
	}

	/**
	 * 检查给定key是否存在
	 * */
	public static Boolean exists(String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.exists(key);
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Set key error : " + e);
			}
		} finally {
			returnResource(jedis);
		}
		return null;
	}

	/**
	 * 设置 过期时间
	 * 
	 * @param key
	 * @param seconds
	 *            以秒为单位
	 * @param value
	 */
	public void setString(String key, int seconds, String value) {
		Jedis jedis = getJedis();
		try {
			value = value == null ? "" : value;
			jedis.setex(key, seconds, value);
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Set key error : " + e);
			}
		} finally {
			returnResource(jedis);
		}
	}

	/**
	 * 获取String值
	 * 
	 * @param key
	 * @return value
	 */
	public static String getString(String key) {
		Jedis jedis = getJedis();
		try {
			if (jedis == null || !jedis.exists(key)) {
				return null;
			}
			return jedis.get(key);
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Set key error : " + e);
			}
		} finally {
			returnResource(jedis);
		}
		return null;
	}

	/**
	 * 检查token：time是否超过两个小时的寿命
	 * 若超过：生成新的token，删除旧的token。。。。。<strong>设置token超时时间默认值</strong>
	 * 
	 * @return isExists：是否存在token; isTimeout：是否超时; newToken：新的token;
	 * */
	public static JSONObject checkToken(String token, String deviceCode) {
		List<String> debugList = new ArrayList<String>();

		Jedis jedis = getJedis();
		if (jedis == null) {
			return null;
		}
		JSONObject json = new JSONObject();
		try {

			Boolean isExists = jedis.exists(Constants.REDIS_TOKEN_PREFIX
					.concat(token));
			// System.out.println("checkToken__isExists: "+isExists);
			if (isExists) {

				String userid = jedis.hget(
						Constants.REDIS_TOKEN_PREFIX.concat(token), "userid");
				Long time = Long.valueOf(jedis.hget(
						Constants.REDIS_TOKEN_PREFIX.concat(token), "time"));

				String deviceCodeInRedis = jedis.hget(Constants.redis_online,
						Constants.redis_online_deviceCode.concat(userid));
				// System.out.println("_deviceCodeInRedis_" +
				// deviceCodeInRedis);
				// System.out.println("checkToken__deviceCode:"+isExists+"__deviceCodeInRedis:"+deviceCodeInRedis);
				if (deviceCode.equals(deviceCodeInRedis)) {// 传入的设备编码跟redis中存储的一致
					json.put("isExists", true);
					/**
					 * Long now = new Date().getTime(); if( now - time > outTime
					 * ){
					 * 
					 * newToken = Common.generateToken(); String _tk =
					 * Constants.REDIS_TOKEN_PREFIX.concat(newToken);
					 * jedis.hset(_tk, "userid", userid); jedis.hset(_tk,
					 * "time", now.toString()); jedis.expire(_tk, EXRP_WEEK);
					 * jedis.del(Constants.REDIS_TOKEN_PREFIX.concat(token));
					 * 
					 * json.put("isTimeout", true); json.put("newToken",
					 * newToken); json.put("userid", userid);
					 * 
					 * }else{ json.put("userid", userid); json.put("isTimeout",
					 * false); }
					 **/
					json.put("userid", userid);
					json.put("isTimeout", false);
				} else {
					debugList.add("deviceCodeIsNull : " + token + "___"
							+ deviceCode + "___" + deviceCodeInRedis);
					json.put("isExists", false);
				}
			} else {// 不存在需要重新登录
				debugList.add("tokenIsNull");
				json.put("isExists", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			returnResource(jedis);
		}
		
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("checkTokenAndDeviceCode: {}",debugList);
		}

		return json;
	}

	public static String updateToken(String token, String userid) {
		/*
		 * Jedis jedis = getJedis(); if (jedis == null) { return token; } String
		 * newToken = ""; Long outTime = (long) (60 * 60 * 1000); String timeStr
		 * = jedis.hget(Constants.REDIS_TOKEN_PREFIX.concat(token), "time");
		 * 
		 * Long now = new Date().getTime(); if (timeStr == null || now -
		 * Long.valueOf(timeStr) > outTime) {
		 * 
		 * newToken = Common.generateToken(); String _tk =
		 * Constants.REDIS_TOKEN_PREFIX.concat(newToken); jedis.hset(_tk,
		 * "userid", userid); jedis.hset(_tk, "time", now.toString());
		 * jedis.expire(_tk, EXRP_MONTH);
		 * jedis.del(Constants.REDIS_TOKEN_PREFIX.concat(token));
		 * 
		 * returnResource(jedis); return newToken;
		 * 
		 * } else { returnResource(jedis); return token; }
		 */
		return token;
	}

	/**
	 * 用户登录Redis保存token
	 * 
	 * @param
	 * */
	public static boolean userLogin(String userid, String token,
			String deviceCode) {
		Jedis jedis = getJedis();
		if (jedis == null) {
			return false;
		}
		try {
			String _tk = Constants.REDIS_TOKEN_PREFIX.concat(token);
			Long now = new Date().getTime();

			jedis.hset(_tk, "userid", userid);
			jedis.hset(_tk, "time", now.toString());
			// jedis.expire(_tk, EXRP_MONTH);

			jedis.hset(Constants.redis_online,
					Constants.redis_online_deviceCode.concat(userid),
					deviceCode);
			// System.out.println(new Date().toString()+"____userLogin___data: "
			// +
			// "userid:"+userid);
			return true;
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			returnResource(jedis);
		}
		return false;
	}

	/**
	 * 获取String值
	 * 
	 * @param key
	 * @return value
	 */
	public static String hget(String key, String field) {
		Jedis jedis = getJedis();
		try {
			if (jedis == null || !jedis.hexists(key, field)) {
				return null;
			}
			return jedis.hget(key, field);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			returnResource(jedis);
		}
		return null;
	}

	/**
     * 
     * */
	public static Long hset(String key, String field, String value) {
		Jedis jedis = getJedis();
		try {
			if (jedis == null) {
				return null;
			}
			return jedis.hset(key, field, value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			returnResource(jedis);
		}

		return null;
	}

	public static JSONObject getOSSinfo() {
		// TODO Auto-generated method stub
		JSONObject access = new JSONObject();
		Jedis jedis = getJedis();
		if (jedis != null) {

			if (jedis.exists(Constants.redis_oss)) {
				access.put("accessKeyId",
						jedis.hget(Constants.redis_oss, "accessKeyId"));
				access.put("accessKeySecret",
						jedis.hget(Constants.redis_oss, "accessKeySecret"));
				access.put("securityToken",
						jedis.hget(Constants.redis_oss, "securityToken"));
				access.put("expiration",
						jedis.hget(Constants.redis_oss, "expiration"));
			} else {
				access = StsService.getAccess();
				if (access != null) {
					jedis.hset(Constants.redis_oss, "accessKeyId",
							access.getString("accessKeyId"));
					jedis.hset(Constants.redis_oss, "accessKeySecret",
							access.getString("accessKeySecret"));
					jedis.hset(Constants.redis_oss, "securityToken",
							access.getString("securityToken"));
					jedis.hset(Constants.redis_oss, "expiration",
							access.getString("expiration"));
					jedis.expire(Constants.redis_oss, 3600);

				} else {
					returnResource(jedis);
					return null;
				}
			}
		} else {
			returnResource(jedis);
			return null;
		}
		returnResource(jedis);
		return access;
	}

	public static void logout(String user_id, String token) {
		// TODO Auto-generated method stub
		Jedis jedis = getJedis();
		if (jedis != null) {

			jedis.del(Constants.REDIS_TOKEN_PREFIX.concat(token));
			jedis.hdel(Constants.redis_online,
					Constants.redis_online_channelId.concat(user_id));
			jedis.hdel(Constants.redis_online,
					Constants.redis_online_deviceType.concat(user_id));
			jedis.hdel(Constants.redis_online,
					Constants.redis_online_deviceCode.concat(user_id));

			returnResource(jedis);
		}
	}

	// 根据userCode从缓存中获取user对象
	public static User getUserByCode(Integer userCode) {
		User user = null;
		String userJsonData = getString(String.valueOf(userCode));
		if (StringUtils.isNotBlank(userJsonData)) {
			user = JSONObject.parseObject(userJsonData, User.class);
		}
		return user;
	}
	//以userCode为Key将user信息设置到缓存中
	public static void setUserByCode(User user){
		if (user !=null) {
			user.setPassword("");
			Integer code = user.getCode();
			String userJsonData = JSONObject.toJSONString(user);
			setString(String.valueOf(code), userJsonData);
		}
	}

}
