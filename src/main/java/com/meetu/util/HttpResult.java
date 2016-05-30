package com.meetu.util;

public class HttpResult {

    // 状态码
    private int code;

    // 响应体内容
    private String body;

    public HttpResult() {
        super();
    }

    public HttpResult(int code, String body) {
        super();
        this.code = code;
        this.body = body;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
