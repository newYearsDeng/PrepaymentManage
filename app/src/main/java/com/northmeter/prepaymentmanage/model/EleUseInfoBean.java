package com.northmeter.prepaymentmanage.model;

import java.util.List;

/**
 * @author zz
 * @time 2016/11/16 14:52
 * @des 电使用信息
 */
public class EleUseInfoBean {

    /**
     * RESCODE : 1
     * RESMSG : 成功
     * RESPONSEXML : [{"ZYE":"-82.18","YFFYE":"-82.18","BZDYE":"0.00","ZYDL":"54.84","DBZT":"(跳闸)余额不足跳闸|余额低于阀值|余额不足时远程跳闸","UPDATEDTIME":"2016-11-11 4:01:50"}]
     */

    private String RESCODE;
    private String RESMSG;
    /**
     * ZYE : -82.18
     * YFFYE : -82.18
     * BZDYE : 0.00
     * ZYDL : 54.84
     * DBZT : (跳闸)余额不足跳闸|余额低于阀值|余额不足时远程跳闸
     * UPDATEDTIME : 2016-11-11 4:01:50
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
        private String ZYE;
        private String YFFYE;
        private String BZDYE;
        private String ZYDL;
        private String DBZT;
        private String UPDATEDTIME;

        public String getZYE() {
            return ZYE;
        }

        public void setZYE(String zYE) {
            ZYE = zYE;
        }

        public String getYFFYE() {
            return YFFYE;
        }

        public void setYFFYE(String yFFYE) {
            YFFYE = yFFYE;
        }

        public String getBZDYE() {
            return BZDYE;
        }

        public void setBZDYE(String bZDYE) {
            BZDYE = bZDYE;
        }

        public String getZYDL() {
            return ZYDL;
        }

        public void setZYDL(String zYDL) {
            ZYDL = zYDL;
        }

        public String getDBZT() {
            return DBZT;
        }

        public void setDBZT(String dBZT) {
            DBZT = dBZT;
        }

        public String getUPDATEDTIME() {
            return UPDATEDTIME;
        }

        public void setUPDATEDTIME(String uPDATEDTIME) {
            UPDATEDTIME = uPDATEDTIME;
        }
    }
}
