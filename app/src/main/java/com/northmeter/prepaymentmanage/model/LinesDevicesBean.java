package com.northmeter.prepaymentmanage.model;

import java.util.List;

/**
 * @author zz
 * @time 2016/11/18 16:32
 * @des 采集设备 在/离 线信息
 */
public class LinesDevicesBean {

    /**
     * RESCODE : 1
     * RESMSG : 成功
     * RESPONSEXML : [{"ConcentratorName":"一，二楼","ConcentratorCode":"650900583","IP":"192.168.0.221"},{"ConcentratorName":"三，四楼","ConcentratorCode":"641101868","IP":"192.168.0.222"},{"ConcentratorName":"水表集中器","ConcentratorCode":"201600517","IP":"192.168.0.223"}]
     */

    private String RESCODE;
    private String RESMSG;
    /**
     * ConcentratorName : 一，二楼
     * ConcentratorCode : 650900583
     * IP : 192.168.0.221
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
        private String ConcentratorName;
        private String ConcentratorCode;
        private String IP;

        public String getConcentratorName() {
            return ConcentratorName;
        }

        public void setConcentratorName(String concentratorName) {
            ConcentratorName = concentratorName;
        }

        public String getConcentratorCode() {
            return ConcentratorCode;
        }

        public void setConcentratorCode(String concentratorCode) {
            ConcentratorCode = concentratorCode;
        }

        public String getIP() {
            return IP;
        }

        public void setIP(String iP) {
            IP = iP;
        }
    }
}
