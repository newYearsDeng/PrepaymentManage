package com.northmeter.prepaymentmanage.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/11/14.
 */
public class EquipmentBean {


    /**
     * RESCODE : 1
     * RESMSG : 成功
     * COUNT : 10
     * NORMAL : 10
     * ACCURACY : 1%
     * RESPONSEXML : [{"COMADDRESS":"020160300795","BuildingName":"2101房","ZYE":"5.27","BJZT":"(阀开)","TXZT":"通讯正常"},{"COMADDRESS":"020160300796","BuildingName":"2104房","ZYE":"-25.80","BJZT":"(阀关)","TXZT":"通讯正常"},{"COMADDRESS":"020160300793","BuildingName":"2106房","ZYE":"-7.80","BJZT":"(阀关)","TXZT":"通讯正常"},{"COMADDRESS":"020160300794","BuildingName":"2109房","ZYE":"-45.60","BJZT":"","TXZT":"通讯正常"},{"COMADDRESS":"020160300787","BuildingName":"2110房","ZYE":"128.80","BJZT":"(阀开)","TXZT":"通讯正常"}]
     */

    private String RESCODE;
    private String RESMSG;
    private String COUNT;
    private String NORMAL;
    private String ACCURACY;

    /**
     * COMADDRESS : 020160300795
     * BuildingName : 2101房
     * ZYE : 5.27
     * BJZT : (阀开)
     * TXZT : 通讯正常
     */

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

    public String getCOUNT() {
        return COUNT;
    }

    public void setCOUNT(String COUNT) {
        this.COUNT = COUNT;
    }

    public String getNORMAL() {
        return NORMAL;
    }

    public void setNORMAL(String NORMAL) {
        this.NORMAL = NORMAL;
    }

    public String getACCURACY() {
        return ACCURACY;
    }

    public void setACCURACY(String ACCURACY) {
        this.ACCURACY = ACCURACY;
    }

    public List<RESPONSEXMLBean> getRESPONSEXML() {
        return RESPONSEXML;
    }

    public void setRESPONSEXML(List<RESPONSEXMLBean> RESPONSEXML) {
        this.RESPONSEXML = RESPONSEXML;
    }

    public static class RESPONSEXMLBean implements Serializable {
        private String COMADDRESS;
        private String BuildingName;
        private String ZYE;
        private String BJZT;
        private String TXZT;
        private boolean isChecked;

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        public String getCOMADDRESS() {
            return COMADDRESS;
        }

        public void setCOMADDRESS(String COMADDRESS) {
            this.COMADDRESS = COMADDRESS;
        }

        public String getBuildingName() {
            return BuildingName;
        }

        public void setBuildingName(String BuildingName) {
            this.BuildingName = BuildingName;
        }

        public String getZYE() {
            return ZYE;
        }

        public void setZYE(String ZYE) {
            this.ZYE = ZYE;
        }

        public String getBJZT() {
            return BJZT;
        }

        public void setBJZT(String BJZT) {
            this.BJZT = BJZT;
        }

        public String getTXZT() {
            return TXZT;
        }

        public void setTXZT(String TXZT) {
            this.TXZT = TXZT;
        }

        @Override
        public String toString() {
            return "RESPONSEXMLBean{" +
                    "COMADDRESS='" + COMADDRESS + '\'' +
                    ", BuildingName='" + BuildingName + '\'' +
                    ", ZYE='" + ZYE + '\'' +
                    ", BJZT='" + BJZT + '\'' +
                    ", TXZT='" + TXZT + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "EquipmentBean{" +
                "RESCODE='" + RESCODE + '\'' +
                ", RESMSG='" + RESMSG + '\'' +
                ", COUNT='" + COUNT + '\'' +
                ", NORMAL='" + NORMAL + '\'' +
                ", ACCURACY='" + ACCURACY + '\'' +
                ", RESPONSEXML=" + RESPONSEXML +
                '}';
    }
}
