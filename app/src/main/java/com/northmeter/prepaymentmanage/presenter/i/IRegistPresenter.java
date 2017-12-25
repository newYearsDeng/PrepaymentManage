package com.northmeter.prepaymentmanage.presenter.i;

/**
 * @author zz
 * @time 2016/11/11 9:39
 * @des 注册presenter的接口
 */
public interface IRegistPresenter {
    /**
     * 确认注册
     * @param phone
     * @param userName
     * @param intoSchoolDate
     * @param studentNum
     * @param intoHouse
     * @param inputPwd
     * @param confirmPwd
     * @param problemOne
     * @param answerOne
     * @param problemTwo
     * @param answerTwo
     * @param problemThree
     * @param answerThree
     */
    void confirmRegist(String phone, String userName, String intoSchoolDate, String studentNum, String intoHouse, String inputPwd, String confirmPwd, String problemOne, String answerOne, String problemTwo, String answerTwo, String problemThree, String answerThree);

    /**
     * 获取校区信息
     */
    void getSchoolBuilding();
    /**
     * 获取楼层等子信息
     * @param area_id
     */
    void getSchoolSonBuilding(String area_id);
    /**
     * 取消rxjava的注册
     */
    void RxUnsubscribe();
}
