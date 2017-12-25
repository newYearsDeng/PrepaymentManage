package com.northmeter.prepaymentmanage.model;

import java.util.List;

/**
 * Created by Administrator on 2016/11/11.
 */
public class TopUp {

    /**
     * RESCODE : 1
     * RESMSG : 成功！
     * RESPONSEXML : [{"ChargeRecordName":"充值","OptionName":"现金","ChargeAmount":"300.0000","ChargeTime":"2016-11-11 10:35:21"},{"ChargeRecordName":"充值","OptionName":"现金","ChargeAmount":"220.0000","ChargeTime":"2016-10-22 11:18:23"},{"ChargeRecordName":"充值","OptionName":"现金","ChargeAmount":"350.0000","ChargeTime":"2016-10-12 10:58:37"},{"ChargeRecordName":"充值","OptionName":"现金","ChargeAmount":"100.0000","ChargeTime":"2016-9-30 14:43:36"},{"ChargeRecordName":"充值","OptionName":"现金","ChargeAmount":"390.0000","ChargeTime":"2016-9-20 14:12:36"},{"ChargeRecordName":"充值","OptionName":"现金","ChargeAmount":"800.0000","ChargeTime":"2016-8-22 16:17:46"},{"ChargeRecordName":"充值","OptionName":"现金","ChargeAmount":"800.0000","ChargeTime":"2016-7-26 18:26:35"},{"ChargeRecordName":"充值","OptionName":"现金","ChargeAmount":"120.0000","ChargeTime":"2016-7-22 16:12:51"},{"ChargeRecordName":"充值","OptionName":"现金","ChargeAmount":"85.0000","ChargeTime":"2016-7-16 21:21:25"},{"ChargeRecordName":"充值","OptionName":"现金","ChargeAmount":"240.0000","ChargeTime":"2016-7-2 16:52:22"},{"ChargeRecordName":"充值","OptionName":"现金","ChargeAmount":"200.0000","ChargeTime":"2016-6-27 15:10:03"},{"ChargeRecordName":"充值","OptionName":"现金","ChargeAmount":"227.0000","ChargeTime":"2016-6-25 17:24:33"}]
     */

    private String RESCODE;
    private String RESMSG;
    /**
     * ChargeRecordName : 充值
     * OptionName : 现金
     * ChargeAmount : 300.0000
     * ChargeTime : 2016-11-11 10:35:21
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
    }
}
