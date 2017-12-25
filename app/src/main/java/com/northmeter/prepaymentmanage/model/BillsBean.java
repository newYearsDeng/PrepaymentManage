package com.northmeter.prepaymentmanage.model;

import java.util.List;

/**
 * @author zz
 * @time 2016/11/14 17:47
 * @des 用能账单
 */
public class BillsBean {

    /**
     * RESCODE : 1
     * RESMSG : 成功
     * RESPONSEXML : [{"QCBD":"54.82","QMBD":"54.84","JSDATA":"0.02","JSMONEY":"0.0300"},{"QCBD":"","QMBD":"","JSDATA":"","JSMONEY":""}]
     */

    private String RESCODE;
    private String RESMSG;
    /**
     * QCBD : 54.82
     * QMBD : 54.84
     * JSDATA : 0.02
     * JSMONEY : 0.0300
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
        private String QCBD;
        private String QMBD;
        private String JSDATA;
        private String JSMONEY;

        public String getQCBD() {
            return QCBD;
        }

        public void setQCBD(String qCBD) {
            QCBD = qCBD;
        }

        public String getQMBD() {
            return QMBD;
        }

        public void setQMBD(String qMBD) {
            QMBD = qMBD;
        }

        public String getJSDATA() {
            return JSDATA;
        }

        public void setJSDATA(String jSDATA) {
            JSDATA = jSDATA;
        }

        public String getJSMONEY() {
            return JSMONEY;
        }

        public void setJSMONEY(String jSMONEY) {
            JSMONEY = jSMONEY;
        }
    }
}
