package com.northmeter.prepaymentmanage.model;

import java.util.List;

/**
 * @author admin
 * @time 2016/11/11 14:58
 * @des 登录的数据
 */
public class LoginBean {

    /**
     * RESCODE : 1
     * RESMSG : 成功
     * RESPONSEXML : [{"OPER_TYPE":"查询员","OPE_RAUTH":"NULL","BuildingID":"123"}]
     */

    private String RESCODE;
    private String RESMSG;
    /**
     * OPER_TYPE : 查询员
     * OPE_RAUTH : NULL
     * BuildingID : 123
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
        private String OPER_TYPE;
        private String OPE_RAUTH;
        private String BuildingID;

        public String getOPER_TYPE() {
            return OPER_TYPE;
        }

        public void setOPER_TYPE(String oPER_TYPE) {
            OPER_TYPE = oPER_TYPE;
        }

        public String getOPE_RAUTH() {
            return OPE_RAUTH;
        }

        public void setOPE_RAUTH(String oPE_RAUTH) {
            OPE_RAUTH = oPE_RAUTH;
        }

        public String getBuildingID() {
            return BuildingID;
        }

        public void setBuildingID(String buildingID) {
            BuildingID = buildingID;
        }

        @Override
        public String toString() {
            return "RESPONSEXMLBean{" +
                    "OPER_TYPE='" + OPER_TYPE + '\'' +
                    ", OPE_RAUTH='" + OPE_RAUTH + '\'' +
                    ", BuildingID='" + BuildingID + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "RESCODE='" + RESCODE + '\'' +
                ", RESMSG='" + RESMSG + '\'' +
                ", RESPONSEXML=" + RESPONSEXML +
                '}';
    }
}
