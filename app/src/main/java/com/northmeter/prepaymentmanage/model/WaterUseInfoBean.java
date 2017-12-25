package com.northmeter.prepaymentmanage.model;

import java.util.List;

/**
 * @author zz
 * @time 2016/11/11 17:35
 * @des 水使用详细信息
 */
public class WaterUseInfoBean {


    /**
     * RESCODE : 1
     * RESMSG : 成功
     * RESPONSEXML : [{"ZYE":"5.27","BZSYE":"0.00","ZYSL":"0.80","SBZT":"(阀开)余额充足","UPDATEDTIME":"2016-11-11 1:02:13"}]
     */

    private String RESCODE;
    private String RESMSG;
    /**
     * ZYE : 5.27
     * BZSYE : 0.00
     * ZYSL : 0.80
     * SBZT : (阀开)余额充足
     * UPDATEDTIME : 2016-11-11 1:02:13
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
        private String BZSYE;
        private String ZYSL;
        private String SBZT;
        private String UPDATEDTIME;

        public String getZYE() {
            return ZYE;
        }

        public void setZYE(String zYE) {
            ZYE = zYE;
        }

        public String getBZSYE() {
            return BZSYE;
        }

        public void setBZSYE(String bZSYE) {
            BZSYE = bZSYE;
        }

        public String getZYSL() {
            return ZYSL;
        }

        public void setZYSL(String zYSL) {
            ZYSL = zYSL;
        }

        public String getSBZT() {
            return SBZT;
        }

        public void setSBZT(String sBZT) {
            SBZT = sBZT;
        }

        public String getUPDATEDTIME() {
            return UPDATEDTIME;
        }

        public void setUPDATEDTIME(String uPDATEDTIME) {
            UPDATEDTIME = uPDATEDTIME;
        }
    }
}
