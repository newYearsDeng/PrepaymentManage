package com.northmeter.prepaymentmanage.model;

import java.util.List;

/**
  *
  *@author lht
  *@time   2016/11/10 19:03
  *@des     网络请求类
  */
public class RequestResult{


    /**
     * RESCODE : 1
     * RESMSG : 成功
     * RESPONSEXML : [{"Area_ID":"001001","Area_NAME":"高新科技园"},{"Area_ID":"001002","Area_NAME":"test"},{"Area_ID":"001003","Area_NAME":"走走走"},{"Area_ID":"001004","Area_NAME":"陕西大国信水表联调"}]
     */

    private String RESCODE;
    private String RESMSG;
    /**
     * Area_ID : 001001
     * Area_NAME : 高新科技园
     */

    private List<RESPONSEXMLBean> RESPONSEXML;

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

    public List<RESPONSEXMLBean> getRESPONSEXML() {
        return RESPONSEXML;
    }

    public void setRESPONSEXML(List<RESPONSEXMLBean> RESPONSEXML) {
        this.RESPONSEXML = RESPONSEXML;
    }

    public static class RESPONSEXMLBean {
        private String Area_ID;
        private String Area_NAME;
        private String ChargeRecordName;
        private String OptionName;
        private String ChargeAmount;
        private String ChargeTime;

        public String getChargeRecordName() {
            return ChargeRecordName;
        }

        public void setChargeRecordName(String ChargeRecordName) {
            this.ChargeRecordName = ChargeRecordName;
        }

        public String getOptionName() {
            return OptionName;
        }

        public void setOptionName(String OptionName) {
            this.OptionName = OptionName;
        }

        public String getChargeAmount() {
            return ChargeAmount;
        }

        public void setChargeAmount(String ChargeAmount) {
            this.ChargeAmount = ChargeAmount;
        }

        public String getChargeTime() {
            return ChargeTime;
        }

        public void setChargeTime(String ChargeTime) {
            this.ChargeTime = ChargeTime;
        }

        public String getArea_ID() {
            return Area_ID;
        }

        public void setArea_ID(String Area_ID) {
            this.Area_ID = Area_ID;
        }

        public String getArea_NAME() {
            return Area_NAME;
        }

        public void setArea_NAME(String Area_NAME) {
            this.Area_NAME = Area_NAME;
        }

        @Override
        public String toString() {
            return "RESPONSEXMLBean{" +
                    "Area_ID='" + Area_ID + '\'' +
                    ", Area_NAME='" + Area_NAME + '\'' +
                    '}';
        }
    }
}
