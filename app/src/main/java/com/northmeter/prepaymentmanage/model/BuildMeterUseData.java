package com.northmeter.prepaymentmanage.model;

import java.util.List;

/**
 * Created by Administrator on 2016/11/22.
 */
public class BuildMeterUseData {


    /**
     * RECORDSUM : 10
     * RESCODE : 1
     * RESMSG : 成功
     * RESPONSEXML : [{"BuildingName":"2101房","DataItemValueTime":"2016-10-1 0:00:00","TotalactiveTotal":"0m3","cTotalactiveTotalMoney":"0元","eDataItemValue":"","sDataItemValue":""},{"BuildingName":"2102房","DataItemValueTime":"2016-10-1 0:00:00","TotalactiveTotal":"0m3","cTotalactiveTotalMoney":"0元","eDataItemValue":"","sDataItemValue":""},{"BuildingName":"2103房","DataItemValueTime":"2016-10-1 0:00:00","TotalactiveTotal":"0m3","cTotalactiveTotalMoney":"0元","eDataItemValue":"","sDataItemValue":""},{"BuildingName":"2104房","DataItemValueTime":"2016-10-1 0:00:00","TotalactiveTotal":"0m3","cTotalactiveTotalMoney":"0元","eDataItemValue":"","sDataItemValue":""},{"BuildingName":"2105房","DataItemValueTime":"2016-10-1 0:00:00","TotalactiveTotal":"0m3","cTotalactiveTotalMoney":"0元","eDataItemValue":"","sDataItemValue":""},{"BuildingName":"2106房","DataItemValueTime":"2016-10-1 0:00:00","TotalactiveTotal":"0m3","cTotalactiveTotalMoney":"0元","eDataItemValue":"","sDataItemValue":""},{"BuildingName":"2107房","DataItemValueTime":"2016-10-1 0:00:00","TotalactiveTotal":"0m3","cTotalactiveTotalMoney":"0元","eDataItemValue":"","sDataItemValue":""},{"BuildingName":"2108房","DataItemValueTime":"2016-10-1 0:00:00","TotalactiveTotal":"0m3","cTotalactiveTotalMoney":"0元","eDataItemValue":"","sDataItemValue":""},{"BuildingName":"2109房","DataItemValueTime":"2016-10-1 0:00:00","TotalactiveTotal":"0m3","cTotalactiveTotalMoney":"0元","eDataItemValue":"","sDataItemValue":""},{"BuildingName":"2110房","DataItemValueTime":"2016-10-1 0:00:00","TotalactiveTotal":"0m3","cTotalactiveTotalMoney":"0元","eDataItemValue":"","sDataItemValue":""}]
     */

    private String RECORDSUM;
    private String RESCODE;
    private String RESMSG;
    private List<RESPONSEXMLBean> RESPONSEXML;

    public String getRECORDSUM() {
        return RECORDSUM;
    }

    public void setRECORDSUM(String RECORDSUM) {
        this.RECORDSUM = RECORDSUM;
    }

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
         * BuildingName : 2101房
         * DataItemValueTime : 2016-10-1 0:00:00
         * TotalactiveTotal : 0m3
         * cTotalactiveTotalMoney : 0元
         * eDataItemValue :
         * sDataItemValue :
         */

        private String BuildingName;
        private String DataItemValueTime;
        private String TotalactiveTotal;
        private String cTotalactiveTotalMoney;
        private String eDataItemValue;
        private String sDataItemValue;

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

        public String getCTotalactiveTotalMoney() {
            return cTotalactiveTotalMoney;
        }

        public void setCTotalactiveTotalMoney(String cTotalactiveTotalMoney) {
            this.cTotalactiveTotalMoney = cTotalactiveTotalMoney;
        }

        public String getEDataItemValue() {
            return eDataItemValue;
        }

        public void setEDataItemValue(String eDataItemValue) {
            this.eDataItemValue = eDataItemValue;
        }

        public String getSDataItemValue() {
            return sDataItemValue;
        }

        public void setSDataItemValue(String sDataItemValue) {
            this.sDataItemValue = sDataItemValue;
        }

        @Override
        public String toString() {
            return "RESPONSEXMLBean{" +
                    "BuildingName='" + BuildingName + '\'' +
                    ", DataItemValueTime='" + DataItemValueTime + '\'' +
                    ", TotalactiveTotal='" + TotalactiveTotal + '\'' +
                    ", cTotalactiveTotalMoney='" + cTotalactiveTotalMoney + '\'' +
                    ", eDataItemValue='" + eDataItemValue + '\'' +
                    ", sDataItemValue='" + sDataItemValue + '\'' +
                    '}';
        }

    }

    @Override
    public String toString() {
        return "BuildMeterUseData{" +
                "RECORDSUM='" + RECORDSUM + '\'' +
                ", RESCODE='" + RESCODE + '\'' +
                ", RESMSG='" + RESMSG + '\'' +
                ", RESPONSEXML=" + RESPONSEXML +
                '}';
    }
}
