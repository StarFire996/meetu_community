package com.meetu.authorization.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.meetu.util.RedisUtil;

/**
 * 手机调用的接口拦截器
 * 
 * 自定义拦截器，判断此次请求是否有权限
 * 
 * @author lzming
 * @date 2016/03/10
 * */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(AuthorizationInterceptor.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        // HandlerMethod handlerMethod = (HandlerMethod) handler;
        // Method method = handlerMethod.getMethod();
        JSONObject json = new JSONObject();
        String[] array = request.getParameterMap().get("data");
        if (array != null && array.length > 0) {
            JSONObject data = JSONObject.parseObject(array[0]);
            String token = escapeSql(data.getString("token"));
            String deviceCode = escapeSql(data.getString("device_code"));
            if (token.length() == 32) {// token length 32
                // check token is exits
                JSONObject checkMap = RedisUtil.checkToken(token, deviceCode);
                // JSONObject checkMap = new JSONObject();
                // checkMap.put("isExists", true);
                // checkMap.put("isTimeout", true);
                // checkMap.put("newToken", Common.generateToken());
                // checkMap.put("userid", "c6f3de0f76de4b25b371c4afcc43f7b8");
                // System.out.println(new
                // Date()+"============="+checkMap.getString("userid")+"================");

                if (checkMap != null) {
                    // System.out.println(new Date().toString() +
                    // "____AuthorizationInterceptor___checkMap:" + checkMap.toJSONString());
                    // System.out.println(new Date().toString() +
                    // "____AuthorizationInterceptor___data:" + data.toJSONString());
                    if (checkMap.getBoolean("isExists")) {

                        if (checkMap.getBoolean("isTimeout")) {
                            // 若token超时，返回新的token
                            request.setAttribute("token", checkMap.getString("newToken"));
                        } else {
                            request.setAttribute("token", token);
                        }
                        request.setAttribute("userid", checkMap.getString("userid"));
                        return true;

                    } else {
                        json.put("state", "303");
                        json.put("error", "缓存中不存在，请重新登录!");
                    }
                } else {
                    json.put("state", "300");
                    json.put("error", "Redis服务出现问题，请联系管理员！");
                }
            } else {
                json.put("state", "300");
                json.put("error", "token校验未通过！");
            }
        } else {// data参数为空
            json.put("state", "300");
            json.put("error", "data参数为空！");
        }
        renderJson(response, json.toString());
        return false;
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) throws Exception {
        // System.out.println("=============afterCompletion================");
    }

    public void renderJson(HttpServletResponse paramHttpServletResponse, String paramString) {

        o00000(paramHttpServletResponse, "application/json;charset=UTF-8", paramString);
    }

    private void o00000(HttpServletResponse paramHttpServletResponse, String paramString1, String paramString2) {
        paramHttpServletResponse.setContentType(paramString1);
        paramHttpServletResponse.setHeader("Pragma", "No-cache");
        paramHttpServletResponse.setHeader("Cache-Control", "no-cache");
        paramHttpServletResponse.setDateHeader("Expires", 0L);
        try {
            paramHttpServletResponse.getWriter().write(paramString2);
        } catch (IOException localIOException) {
            this.logger.error(localIOException.getMessage(), localIOException);
        }
    }

    public String escapeSql(String paramString) {
        String str = StringEscapeUtils.escapeSql(paramString);
        return str;
    }

}
