package com.northmeter.prepaymentmanage.model;

import java.util.List;

/**
 * Created by lht on 2016/11/21.
 * des:生成任务Id的实体类
 */
public class NewIdBean {


    /**
     * RESCODE : 1
     * RESMSG : 成功
     * RESPONSEXML : [{"NEWID":"2e7bcf1c-1251-4ac0-895d-1c6846903fdc"}]
     */

    private String RESCODE;
    private String RESMSG;
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
        /**
         * NEWID : 2e7bcf1c-1251-4ac0-895d-1c6846903fdc
         */

        private String NEWID;

        public String getNEWID() {
            return NEWID;
        }

        public void setNEWID(String NEWID) {
            this.NEWID = NEWID;
        }
    }
}
