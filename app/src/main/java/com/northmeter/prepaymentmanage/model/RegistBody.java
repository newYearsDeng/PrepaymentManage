package com.northmeter.prepaymentmanage.model;

/**
 * @author zz
 * @time 2016/11/11 10:56
 * @des ${TODO}
 */
public class RegistBody {
    String phone;
    String name;
    String password;
    String inSchoolDate;
    String buildindID;
    String problemOne;
    String answerOne;
    String problemTwo;
    String answerTwo;
    String problemThree;
    String answerThree;
    String studentNumber;

    public RegistBody(String phone, String name, String password, String inSchoolDate, String buildindID, String problemOne, String answerOne, String problemTwo, String answerTwo, String problemThree, String answerThree, String studentNumber) {
        this.phone = phone;
        this.name = name;
        this.password = password;
        this.inSchoolDate = inSchoolDate;
        this.buildindID = buildindID;
        this.problemOne = problemOne;
        this.answerOne = answerOne;
        this.problemTwo = problemTwo;
        this.answerTwo = answerTwo;
        this.problemThree = problemThree;
        this.answerThree = answerThree;
        this.studentNumber = studentNumber;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInSchoolDate() {
        return inSchoolDate;
    }

    public void setInSchoolDate(String inSchoolDate) {
        this.inSchoolDate = inSchoolDate;
    }

    public String getBuildindID() {
        return buildindID;
    }

    public void setBuildindID(String buildindID) {
        this.buildindID = buildindID;
    }

    public String getProblemOne() {
        return problemOne;
    }

    public void setProblemOne(String problemOne) {
        this.problemOne = problemOne;
    }

    public String getAnswerOne() {
        return answerOne;
    }

    public void setAnswerOne(String answerOne) {
        this.answerOne = answerOne;
    }

    public String getProblemTwo() {
        return problemTwo;
    }

    public void setProblemTwo(String problemTwo) {
        this.problemTwo = problemTwo;
    }

    public String getAnswerTwo() {
        return answerTwo;
    }

    public void setAnswerTwo(String answerTwo) {
        this.answerTwo = answerTwo;
    }

    public String getProblemThree() {
        return problemThree;
    }

    public void setProblemThree(String problemThree) {
        this.problemThree = problemThree;
    }

    public String getAnswerThree() {
        return answerThree;
    }

    public void setAnswerThree(String answerThree) {
        this.answerThree = answerThree;
    }
}
