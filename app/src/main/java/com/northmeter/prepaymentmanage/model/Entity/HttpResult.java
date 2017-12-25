package com.northmeter.prepaymentmanage.model.Entity;

/**
 * Created by Lht
 * on 2016/12/19.
 * des:
 */
public class HttpResult<T> {
    private String RESCODE;
    private String RESMSG;
    private T RESPONSEXML;



    public String getRESCODE() {
        return RESCODE;
    }

    public void setRESCODE(String RESCODE) {
        this.RESCODE = RESCODE;
    }

    public String getRESMSG() {
        return RESMSG;
    }

    public void setRESMSG(String RESMSG) {
        this.RESMSG = RESMSG;
    }

    public T getRESPONSEXML() {
        return RESPONSEXML;
    }

    public void setRESPONSEXML(T data) {
        this.RESPONSEXML = data;
    }
}

