package com.northmeter.prepaymentmanage.model.bdBean;

/**
 * Created by dyd on 2018/3/30.
 * 登录bean
 */
public class BDLoginBean {
    /**
     msg	String	返回信息
     code	Int	返回代码
     expire	Int	过期时间（单位：秒），默认是20分钟
     token	string	令牌字符串*/

    private String msg;
    private int code;
    private int expire;
    private String token;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
