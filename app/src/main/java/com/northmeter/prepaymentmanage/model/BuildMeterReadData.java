package com.northmeter.prepaymentmanage.model;

import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */
public class BuildMeterReadData {

    /**
     * RESCODE : 1
     * RESMSG : 成功
     * RECORDSUM : 1
     * RESPONSEXML : [{"BuildingName":"2101房","DataItemValueTime":"2016-10-20 0:00:00","TotalactiveTotal":"54.83"}]
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
         * BuildingName : 2101房
         * DataItemValueTime : 2016-10-20 0:00:00
         * TotalactiveTotal : 54.83
         */

        private String BuildingName;
        private String DataItemValueTime;
        private String TotalactiveTotal;

        public String getBuildingName() {
            return BuildingName;
        }

        public void setBuildingName(String BuildingName) {
            this.BuildingName = BuildingName;
        }

        public String getDataItemValueTime() {
            return DataItemValueTime;
        }

        public void setDataItemValueTime(String DataItemValueTime) {
            this.DataItemValueTime = DataItemValueTime;
        }

        public String getTotalactiveTotal() {
            return TotalactiveTotal;
        }

        public void setTotalactiveTotal(String TotalactiveTotal) {
            this.TotalactiveTotal = TotalactiveTotal;
        }
    }
}
