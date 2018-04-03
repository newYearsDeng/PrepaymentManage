package com.northmeter.prepaymentmanage.util.net;

import com.northmeter.prepaymentmanage.model.BuildMeterReadData;
import com.northmeter.prepaymentmanage.model.BuildMeterUseData;
import com.northmeter.prepaymentmanage.model.ControlTaskBean;
import com.northmeter.prepaymentmanage.model.DevicesStateBean;
import com.northmeter.prepaymentmanage.model.EleUseInfoBean;
import com.northmeter.prepaymentmanage.model.Entity.HttpResult;
import com.northmeter.prepaymentmanage.model.Entity.RequestBuilding;
import com.northmeter.prepaymentmanage.model.EquipmentBean;
import com.northmeter.prepaymentmanage.model.BillsBean;
import com.northmeter.prepaymentmanage.model.ForgetPwdProblemBean;
import com.northmeter.prepaymentmanage.model.LinesDevicesBean;
import com.northmeter.prepaymentmanage.model.LoginBean;
import com.northmeter.prepaymentmanage.model.LookChumBean;
import com.northmeter.prepaymentmanage.model.NewIdBean;
import com.northmeter.prepaymentmanage.model.ReadingState;
import com.northmeter.prepaymentmanage.model.RequestResult;
import com.northmeter.prepaymentmanage.model.TopUp;
import com.northmeter.prepaymentmanage.model.UserMeterBean;
import com.northmeter.prepaymentmanage.model.WaterUseInfoBean;
import com.northmeter.prepaymentmanage.model.bdBean.BDLoginBean;
import com.northmeter.prepaymentmanage.ui.GateLock.model.ReadDayLogBean;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
import rx.Scheduler;

/**
 * Created by admin on 2016/11/10.
 */
public interface ApiService {
    //第一级目录的建筑信息
    @GET("GetAreaInfo?")
    Observable<HttpResult<List<RequestBuilding>>> getAreaInfo();

    //子类建筑的信息
    @GET("GetSonInfo?")
    Observable<HttpResult<List<RequestBuilding>>> getSonInfo(@Query("Build_ID") String id);

    @GET("GetChargeRecord?")
    Observable<TopUp> getGetChargeRecord(@Query("COMADDRESS") String comaddress, @Query("YEAR") String year);

    //注册
    @FormUrlEncoded
    @POST("UpLoadCustomerInfo")
    Observable<RequestResult> postRegist(@Field("CUSTOMER_TELPHONE") String phone,
                                         @Field("CUSTOMER_NAME") String name,
                                         @Field("CUSTOMER_PWD") String password,
                                         @Field("CUSTOMER_InSchoolDate") String inSchoolDate,
                                         @Field("CUSTOMER_BuildingID") String buildindID,
                                         @Field("PasswordProblem1") String problemOne,
                                         @Field("PasswordAnswer1") String answerOne,
                                         @Field("PasswordProblem2") String problemTwo,
                                         @Field("PasswordAnswer2") String answerTwo,
                                         @Field("PasswordProblem3") String problemThree,
                                         @Field("PasswordAnswer3") String answerThree);

    //登录
    @FormUrlEncoded
    @POST("LoginCheck")
    Observable<LoginBean> postLogin(@Field("Login_name") String name, @Field("Login_pwd") String password);

    //用户当前房间的用 水 信息
    @GET("GetWaterMeterData")
    Observable<WaterUseInfoBean> getWaterDevicesInfo(@Query("COMADDRESS") String waterComaddress);

    //用户当前房间的用 电 信息
    @GET("GetElectricityMeterData")
    Observable<EleUseInfoBean> getEleDevicesInfo(@Query("COMADDRESS") String eleComaddress);

    //用户绑定房间的表计
    @GET("GetBuildMeterInfo")
    Observable<UserMeterBean> getUserMeter(@Query("Login_name") String loginName);

    //用户房间月份用能账单
    @GET("GetMonthUseData")
    Observable<BillsBean> getMonthUseData(@Query("COMADDRESS") String comaddress, @Query("MONTH") String yearAndMonth);

    @GET("GetMeterInfo?")
    Observable<EquipmentBean> getMeterInfo(@Query("FatherBuildingID") String areaId,
                                           @Query("METERTYPE") String meterType,
                                           @Query("ControlType") String controltype,
                                           @Query("PageIndex") String pageIndex,
                                           @Query("PageSize") String pageSize);

    //普通用的密码修改
    @FormUrlEncoded
    @POST("UpdatePWD")
    Observable<RequestResult> changeUserPwd(@Field("Login_name") String name,
                                            @Field("Login_Opwd") String oldPwd,
                                            @Field("Login_Npwd") String newPwd);

    //管理员的密码修改
    @FormUrlEncoded
    @POST("UpdatePWDOPERATOR")
    Observable<RequestResult> changeManagePwd(@Field("Login_name") String name,
                                              @Field("Login_Opwd") String oldPwd,
                                              @Field("Login_Npwd") String newPwd);

    //意见反馈
    @FormUrlEncoded
    @POST("UpLoadAdivce")
    Observable<RequestResult> setFeedbackContent(@Field("Login_name") String name,
                                                 @Field("CONTENT") String content);

    //解除用户房间绑定
    @GET("DelBuilding")
    Observable<RequestResult> removeRoomBind(@Query("Login_name") String name);

    //用户房间重新绑定
    @GET("UpdateBuilding")
    Observable<RequestResult> againBindRoom(@Query("Login_name") String name,
                                            @Query("BuildingID") String buildingID);

    //指定建筑对象下安装的表计设备的原始抄表数据
    @GET("GetBuildMeterReadData")
    Observable<BuildMeterReadData> getBuildMeterReadData(@Query("BUILDINGID") String buildingId,
                                                         @Query("DateType") String dataType,
                                                         @Query("StartDate") String startDate,
                                                         @Query("METERTYPE") String meterType,
                                                         @Query("PageIndex") int pageIndex,
                                                         @Query("PageSize") int pageSize);

    //获取指定建筑对象下安装的表计设备的用能结算数据
    @GET("GetBuildMeterUseData")
    Observable<BuildMeterUseData> getBuildMeterUseData(@Query("BUILDINGID") String buildingId,
                                                       @Query("DateType") String dataType,
                                                       @Query("StartDate") String startDate,
                                                       @Query("METERTYPE") String meterType,
                                                       @Query("PageIndex") int pageIndex,
                                                       @Query("PageSize") int pageSize);

    //获取采集设备 在/离 线状态
    @GET("GetConcentratorSummary")
    Observable<DevicesStateBean> getDevicesState();

    //获取采集设备 在/离 线信息
    @GET("GetConcentratorInfo")
    Observable<LinesDevicesBean> getLinesDevicesInfo(@Query("SBLX") String isLine);

    //获取抄表任务id
    @GET("ReadMeter?")
    Observable<NewIdBean> getNewId(@Query("COMADDRESS") String comaddress, @Query("METERTYPE") String meterType);

    //获取抄表状态
    @GET("ReadMeterData?")
    Observable<ReadingState> getReadingState(@Query("NEWID") String newId);

    //获取忘记密码密保问题
    @GET("GetPasswordProblem")
    Observable<ForgetPwdProblemBean> getProblem(@Query("CUSTOMER_TELPHONE") String phone);

    //通过密保重置密码
    @FormUrlEncoded
    @POST("VerifyPasswordProblem")
    Observable<RequestResult> resetPassword(@Field("CUSTOMER_TELPHONE") String phone,
                                            @Field("PassWord") String password,
                                            @Field("PasswordProblem") String problem,
                                            @Field("PasswordAnswer") String answer);

    //对水表或电表发送远程控制命令
    @GET("ControlMeter?")
    Observable<NewIdBean> getControlMeterNewId(@Query("COMADDRESS") String comaddress,
                                               @Query("ControlType") String controlType,
                                               @Query("OPNAME") String openName);
    //获取远程操作任务状态
    @GET("GetControlTaskResult?")
    Observable<ControlTaskBean> getControlTaskResult(@Query("BuildingID")String buildingId,
                                                     @Query("METERTYPE")String meterType);
    //查看室友
    @GET("GetRoommateName")
    Observable<LookChumBean> getChumsName(@Query("RoomID")String buildingId);


    //对门锁发送远程控制命令
    @GET("Remotelylock?")
    Observable<NewIdBean> getRemotelylockNewId(@Query("COMADDRESS") String comaddress,
                                               @Query("OPERATORTYPE") String meterType);

    //查询门锁控制任务状态
    @GET("ReadMeterData?")
    Observable<ReadingState> getRemotelylockStatus(@Query("NEWID") String newId);

    //获取门锁操作日志
    @GET("GetUnlockRecordBuilding?")
    Observable<ReadDayLogBean> getUnlockRecordBuilding(@Query("BUILDINGID")String buildingId,
                                                       @Query("DateType")String dataType,
                                                       @Query("StartDate")String startDate,
                                                       @Query("METERTYPE") String meterType,
                                                       @Query("PageIndex")int pageIndex,
                                                       @Query("PageSize")int pageSize);



    //----------------------------北电云接口-------------------------------

    //用户登录
    @FormUrlEncoded
    @POST("/api/app/login")
    Observable<BDLoginBean> getLogin(@Field("userName")String userName,
                                     @Field("time")String time,
                                     @Field("num")String num,
                                     @Field("sign")String sign);

    //获取采集设备
    @GET("/api/app/concentratorinfo/getDeviceList")
    Observable getDeviceList(@Query("token")String token,
                             @Query("page")String page,
                             @Query("row")String row,
                             @Query("concentratorType")String concentratorType,
                             @Query("concentratorCode")String concentratorCode,
                             @Query("projCode")String projCode);

    //获取计量设备信息
    @GET("/api/app/meterinfo/getSublist")
    Observable getSublist(@Query("token")String token,
                          @Query("row")String row,
                          @Query("page")String page,
                          @Query("deviceType")String deviceType,
                          @Query("comAddress")String comAddress,
                          @Query("projCode")String projCode);

    //获取计量设备组网关系
    @GET("/api/app/meterinfo/getSublistByDevice")
    Observable getSublistByDevice(@Query("token")String token,
                                  @Query("row")String row,
                                  @Query("page")String page,
                                  @Query("concentratorType")String concentratorType,
                                  @Query("concentratorCode")String concentratorCode,
                                  @Query("projCode")String projCode);

    //获取冻结抄表数据
    @GET("/api/app/hdmdata/getHDMData")
    Observable getHDMData(@Query("token")String token,
                          @Query("type")String type,
                          @Query("row")String row,
                          @Query("page")String page,
                          @Query("timeFrom")String timeFrom,
                          @Query("timeTo")String timeTo,
                          @Query("deviceType")String deviceType,
                          @Query("comAddress")String comAddress,
                          @Query("projCode")String projCode);

    //获取项目信息
    @GET("/api/app/project/getProjectList")
    Observable getProjectList(@Query("token")String token,
                              @Query("type")String type,
                              @Query("row")String row);

    //获取实时点抄数据
    @FormUrlEncoded
    @POST("/api/app/edataread/getActData")
    Observable getEdatareadActData(@Field("token")String token,
                                   @Field("deviceType")String deviceType,
                                   @Field("comAddress")String comAddress,
                                   @Field("projCode")String projCode,
                                   @Field("dataId")String dataId,
                                   @Field("maxWaitingTime")String maxWaitingTime);

    //获取表计最近一次采集的用能数据
    @FormUrlEncoded
    @POST("/api/app/dataread/getActData")
    Observable getDatareadActData(@Field("token")String token,
                                  @Field("deviceType")String deviceType,
                                  @Field("comAddress")String comAddress,
                                  @Field("projCode")String projCode,
                                  @Field("dataId")String dataId,
                                  @Field("maxWaitingTime")String maxWaitingTime);









}
