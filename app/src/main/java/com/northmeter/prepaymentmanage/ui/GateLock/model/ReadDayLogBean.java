package com.northmeter.prepaymentmanage.ui.GateLock.model;

import java.util.List;

/**
 * Created by dyd on 2017/7/1.
 */
public class ReadDayLogBean {
    /**
     * RESCODE : 1
     * RESMSG : 成功
     * RECORDSUM : 10
     * RESPONSEXML : [{"BuildingName":"宿舍4号楼1楼2103房","UnlockWay":"","UnlockTime":"2016-11-25 9:17:55"},{"BuildingName":"宿舍4号楼1楼2103房","UnlockWay":"","UnlockTime":"2016-11-25 9:17:55"}]
     */

    private String RESCODE;
    private String RESMSG;
    private String RECORDSUM;
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

    public String getRECORDSUM() {
        return RECORDSUM;
    }

    public void setRECORDSUM(String RECORDSUM) {
        this.RECORDSUM = RECORDSUM;
    }

    public List<RESPONSEXMLBean> getRESPONSEXML() {
        return RESPONSEXML;
    }

    public void setRESPONSEXML(List<RESPONSEXMLBean> RESPONSEXML) {
        this.RESPONSEXML = RESPONSEXML;
    }



    public static class RESPONSEXMLBean {
        /**
         * BuildingName : 宿舍4号楼1楼2103房
         * UnlockWay : 远程开阀
         * UnlockTime : 2016-11-25 9:17:55
         */

        private String BuildingName;
        private String UnlockWay;
        private String UnlockTime;

        public String getBuildingName() {
            return BuildingName;
        }

        public void setBuildingName(String buildingName) {
            BuildingName = buildingName;
        }

        public String getUnlockWay() {
            return UnlockWay;
        }

        public void setUnlockWay(String unlockWay) {
            UnlockWay = unlockWay;
        }

        public String getUnlockTime() {
            return UnlockTime;
        }

        public void setUnlockTime(String unlockTime) {
            UnlockTime = unlockTime;
        }
    }
}
