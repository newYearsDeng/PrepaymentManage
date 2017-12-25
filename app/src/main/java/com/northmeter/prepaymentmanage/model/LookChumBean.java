package com.northmeter.prepaymentmanage.model;

import java.util.List;

/**
 * @author zz
 * @time 2016/12/01 15:01
 * @des 查看室友的bean
 */
public class LookChumBean {

    /**
     * RESCODE : 1
     * RESMSG : 成功
     * RESPONSEXML : [{"RoommateName":"aa"},{"RoommateName":"bb"},{"RoommateName":"测试2"},{"RoommateName":"Vae"},{"RoommateName":"测试3"}]
     */

    private String RESCODE;
    private String RESMSG;
    /**
     * RoommateName : aa
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
        private String RoommateName;

        public String getRoommateName() {
            return RoommateName;
        }

        public void setRoommateName(String roommateName) {
            RoommateName = roommateName;
        }
    }
}
