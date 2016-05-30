package com.meetu.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ApiService {

    @Autowired
    private CloseableHttpClient httpClient;

    @Autowired
    private RequestConfig config;

    /**
     * 带参数的get请求，响应码为200，则返回响应体的数据，否则返回空
     * 
     * @param url
     * @param map
     * @return
     */
    public String doGet(String url, Map<String, Object> map) {
        try {
            // 声明URIBuilder
            URIBuilder uriBuilder = new URIBuilder(url);

            // 判断请求参数是否为弄
            if (map != null) {
                // 遍历map
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    // 设置请求参数
                    uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
                }
            }

            // 声明 http get
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            // 设置请求配置信息
            httpGet.setConfig(config);

            // 发起请求
            CloseableHttpResponse response = this.httpClient.execute(httpGet);

            // 判断返回状态码是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                // 判断响应体是否为空
                if (response.getEntity() != null) {
                    // 返回响应体的内容
                    return EntityUtils.toString(response.getEntity(), "UTF-8");
                }
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // 如果有问题则返回null
        return null;
    }

    /**
     * 不带参数的get请求，响应码为200，则返回响应体的数据，否则返回空
     * 
     * @param url
     * @return
     */
    public String doGet(String url) {
        return this.doGet(url, null);
    }

    /**
     * 带参数的post请求,请求体是form表单
     * 
     * @param url
     * @return
     */
    public HttpResult doPost(String url, Map<String, Object> map) {
        // 声明http post请求
        HttpPost httpPost = new HttpPost(url);
        // 设置请求配置参数
        httpPost.setConfig(config);

        try {
            // 判断map不能为空
            if (map != null) {
                // 声明存放参数的list
                List<NameValuePair> param = new ArrayList<NameValuePair>();
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    // 设置参数
                    param.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
                }

                // 创建存放参数的formEntity
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(param, "UTF-8");

                // 把表单实体放到httppost请求中
                httpPost.setEntity(urlEncodedFormEntity);
            }

            // 发起请求
            CloseableHttpResponse response = this.httpClient.execute(httpPost);
            // 判断响应体不能为空
            if (response.getEntity() != null) {
                String result = EntityUtils.toString(response.getEntity(), "UTF-8");
                return new HttpResult(response.getStatusLine().getStatusCode(), result);
            }
            // 如果响应体为空
            return new HttpResult(response.getStatusLine().getStatusCode(), "");

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;

    }

    /**
     * 不带参数的post请求
     * 
     * @param url
     * @return
     */
    public HttpResult doPost(String url) {
        return this.doPost(url, null);
    }

    // 带参数的post请求,请求体是json格式的数据
    public HttpResult doPostJson(String url, String jsonData) {
        // 声明http post请求
        HttpPost httpPost = new HttpPost(url);
        // 设置请求配置参数
        httpPost.setConfig(config);

        try {
            // 判断jsonData不能为空
            if (StringUtils.isNotBlank(jsonData)) {
                // 创建封装json格式数据的entity
                StringEntity stringEntity = new StringEntity(jsonData, ContentType.APPLICATION_JSON);

                // 把json数据实体放到httppost请求中
                httpPost.setEntity(stringEntity);
            }

            // 发起请求
            CloseableHttpResponse response = this.httpClient.execute(httpPost);
            // 判断响应体不能为空
            if (response.getEntity() != null) {
                String result = EntityUtils.toString(response.getEntity(), "UTF-8");
                return new HttpResult(response.getStatusLine().getStatusCode(), result);
            }
            // 如果响应体为空
            return new HttpResult(response.getStatusLine().getStatusCode(), "");

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;

    }

    /**
     * 带参数的put请求
     * 
     * @param url
     * @param map
     * @return
     */
    public HttpResult doPut(String url, Map<String, Object> map) {
        HttpPut httpPut = new HttpPut(url);
        httpPut.setConfig(config);

        if (map != null) {

            try {
                List<NameValuePair> list = new ArrayList<NameValuePair>();
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
                }

                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, "UTF-8");

                httpPut.setEntity(urlEncodedFormEntity);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        CloseableHttpResponse response = null;
        try {
            response = this.httpClient.execute(httpPut);

            // 使用entityutils，第二个参数不能为空
            if (response.getEntity() != null) {
                return new HttpResult(response.getStatusLine().getStatusCode(),
                        EntityUtils.toString(response.getEntity(), "UTF-8"));
            } else {
                return new HttpResult(response.getStatusLine().getStatusCode(), null);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;

    }

    /**
     * 不带参数的put请求
     * 
     * @param url
     * @return
     */
    public HttpResult doPut(String url) {
        return this.doPut(url, null);
    }

    // 不带参数的delete请求
    public HttpResult doDelete(String url) {
        HttpDelete httpDelete = new HttpDelete(url);
        httpDelete.setConfig(config);

        CloseableHttpResponse response = null;
        try {
            response = this.httpClient.execute(httpDelete);

            if (response.getEntity() != null) {
                return new HttpResult(response.getStatusLine().getStatusCode(),
                        EntityUtils.toString(response.getEntity(), "UTF-8"));
            } else {
                return new HttpResult(response.getStatusLine().getStatusCode(), null);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
