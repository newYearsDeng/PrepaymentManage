package com.northmeter.prepaymentmanage.model;

import java.util.List;

/**
 * @author zz
 * @time 2016/11/14 14:08
 * @des 用户表记信息
 */
public class UserMeterBean {

    /**
     * RESCODE : 1
     * RESMSG : 成功！
     * RESPONSEXML : [{"COMADDRESS":"020160300795","METERTYPE":"水"},{"COMADDRESS":"160518004092","METERTYPE":"电"}]
     */

    private String RESCODE;
    private String RESMSG;
    /**
     * COMADDRESS : 020160300795
     * METERTYPE : 水
     */

    private List<RESPONSEXMLBean> RESPONSEXML;

    public String getRESCODE() {
        return RESCODE;
    }

    public void setRESCODE(String rESCODE) {
        RESCODE = rESCODE;
    }

    public String getRESMSG() {
        return RESMSG;
    }

    public void setRESMSG(String rESMSG) {
        RESMSG = rESMSG;
    }

    public List<RESPONSEXMLBean> getRESPONSEXML() {
        return RESPONSEXML;
    }

    public void setRESPONSEXML(List<RESPONSEXMLBean> rESPONSEXML) {
        RESPONSEXML = rESPONSEXML;
    }

    public static class RESPONSEXMLBean {
        private String COMADDRESS;
        private String METERTYPE;

        public String getCOMADDRESS() {
            return COMADDRESS;
        }

        public void setCOMADDRESS(String cOMADDRESS) {
            COMADDRESS = cOMADDRESS;
        }

        public String getMETERTYPE() {
            return METERTYPE;
        }

        public void setMETERTYPE(String mETERTYPE) {
            METERTYPE = mETERTYPE;
        }
    }
}
