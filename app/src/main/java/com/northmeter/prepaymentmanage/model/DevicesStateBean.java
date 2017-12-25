package com.northmeter.prepaymentmanage.model;

import java.util.List;

/**
 * @author zz
 * @time 2016/11/18 16:03
 * @des 采集设备 在/离线 状态bean
 */
public class DevicesStateBean {

    /**
     * RESCODE : 1
     * RESMSG : 成功
     * RESPONSEXML : [{"SUM":"4","OnLineSUM":"4"}]
     */

    private String RESCODE;
    private String RESMSG;
    /**
     * SUM : 4
     * OnLineSUM : 4
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
        private String SUM;
        private String OnLineSUM;

        public String getSUM() {
            return SUM;
        }

        public void setSUM(String sUM) {
            SUM = sUM;
        }

        public String getOnLineSUM() {
            return OnLineSUM;
        }

        public void setOnLineSUM(String onLineSUM) {
            OnLineSUM = onLineSUM;
        }
    }
}
