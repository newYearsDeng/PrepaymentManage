package com.northmeter.prepaymentmanage.model;

import java.util.List;

/**
 * Created by Administrator on 2016/11/25.
 */
public class ControlTaskBean {

    /**
     * RESCODE : 1
     * RESMSG : 成功
     * RESPONSEXML : [{"LINENAME":"宿舍4号楼1楼2103房","ControlType":"远程开阀","ControlTime":"2016-11-25 9:17:55","Result":"未执行"},{"LINENAME":"宿舍4号楼1楼2108房","ControlType":"远程开阀","ControlTime":"2016-11-25 9:17:55","Result":"未执行"},{"LINENAME":"宿舍4号楼1楼2106房","ControlType":"远程开阀","ControlTime":"2016-11-25 9:17:55","Result":"未执行"},{"LINENAME":"宿舍4号楼1楼2105房","ControlType":"远程开阀","ControlTime":"2016-11-25 9:17:55","Result":"未执行"},{"LINENAME":"宿舍4号楼1楼2103房","ControlType":"远程开阀","ControlTime":"2016-11-25 9:17:50","Result":"未执行"},{"LINENAME":"宿舍4号楼1楼2105房","ControlType":"远程开阀","ControlTime":"2016-11-25 9:17:50","Result":"未执行"},{"LINENAME":"宿舍4号楼1楼2105房","ControlType":"远程关阀","ControlTime":"2016-11-25 9:17:47","Result":"未执行"}]
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
         * LINENAME : 宿舍4号楼1楼2103房
         * ControlType : 远程开阀
         * ControlTime : 2016-11-25 9:17:55
         * Result : 未执行
         */

        private String LINENAME;
        private String ControlType;
        private String ControlTime;
        private String Result;

        public String getLINENAME() {
            return LINENAME;
        }

        public void setLINENAME(String LINENAME) {
            this.LINENAME = LINENAME;
        }

        public String getControlType() {
            return ControlType;
        }

        public void setControlType(String ControlType) {
            this.ControlType = ControlType;
        }

        public String getControlTime() {
            return ControlTime;
        }

        public void setControlTime(String ControlTime) {
            this.ControlTime = ControlTime;
        }

        public String getResult() {
            return Result;
        }

        public void setResult(String Result) {
            this.Result = Result;
        }
    }
}
