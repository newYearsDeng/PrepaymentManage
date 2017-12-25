package com.northmeter.prepaymentmanage.model;

import java.util.List;

/**
 * @author zz
 * @time 2016/11/23 11:32
 * @des 忘记密码密保问题
 */
public class ForgetPwdProblemBean {

    /**
     * RESCODE : 1
     * RESMSG : ³É¹¦
     * RESPONSEXML : [{"PasswordProblem1":"null","PasswordProblem2":"null","PasswordProblem3":"null"}]
     */

    private String RESCODE;
    private String RESMSG;
    /**
     * PasswordProblem1 : null
     * PasswordProblem2 : null
     * PasswordProblem3 : null
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
        private String PasswordProblem1;
        private String PasswordProblem2;
        private String PasswordProblem3;

        public String getPasswordProblem1() {
            return PasswordProblem1;
        }

        public void setPasswordProblem1(String passwordProblem1) {
            PasswordProblem1 = passwordProblem1;
        }

        public String getPasswordProblem2() {
            return PasswordProblem2;
        }

        public void setPasswordProblem2(String passwordProblem2) {
            PasswordProblem2 = passwordProblem2;
        }

        public String getPasswordProblem3() {
            return PasswordProblem3;
        }

        public void setPasswordProblem3(String passwordProblem3) {
            PasswordProblem3 = passwordProblem3;
        }
    }
}
