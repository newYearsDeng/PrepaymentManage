package com.northmeter.prepaymentmanage.util;

/**
 * @author lht
 * @time 2016/11/04 15:55
 * @des 常量类
 */
public class Contants {
    //跳转activity的参数
    public static final String EQUIPMENT_INTENT_EXTRA = "equipments";
    public static final String DEVICES_QUERY_METER_TYPE_INTENT_EXTRA = "devices_query_meter_type";
    public static final String DEVICES_QUERY_COMADDRESS_INTENT_EXTRA = "devices_query_comaddress";
    public static final String LOGIN_BUILD_ID_INTENT_EXTRA = "login_build_id";
    public static final String LOGIN_NAME_INTENT_EXTRA = "login_name";
    public static final String HOME_USER_NAME_INTENT_EXTRA = "home_user_name";
    public static final String USER_TYPE_INTENT_EXTRA = "userType";
    public static final String USER_TYPE_USER = "user";
    public static final String USER_TYPE_MANAGE = "manage";
    //sp加密key
    public static final String DECRYPT_ID_KEY = "login_edit_text_id_key";
    public static final String SP_AES_ID_KEY = AES.encrypt("user_id", "login_sp_id_key");
    public static final String SP_AES_BUILDING_KEY = "aes_building_id";
    //北电服务器
    public static final String BASEURL="http://218.17.157.121:7089/Service1.asmx/";
    //厦门大学服务器
    //public static final String BASEURL="http://47.100.136.41:8012/Service1.asmx/";
    //http://10.168.1.203:7089/
    //public static final String BASEURL="http://218.17.157.121:9149/Service1.asmx/";
    //http://218.17.157.121:9149/Service1.asmx/GetAreaInfo?
    //http://218.17.157.121:9147/Service1.asmx/VersionNumber?


    public static final String SELECTEDAREA_ID="selectedArea_ID";
    public static final String SELECTEDBUILDING="selectedBuilding";
    public static final String SELECTEDROOM="selectedRoom";
    public static final String SELECTEDFLOOR="selectedFloor";
    public static final String SELECTEDSCHOOL="selectedSchool";

    public static final String LASTREFRESH_TIME="lastRefreshTime";

    public static final String METERTYPE="meterType";

    public static final String OPERATION_TYPE="operationType";

    public static  final  String READMETER="readMeter";
    public static final  String USEMETER="useMeter";

    public static final String DAY_TYPE="dayType";

    public static final String STARTDATE="startDate";

    // APP_ID 应用从官方网站申请到的合法appId (微信支付)
    public static final String APP_ID_WX = "wx05b357d1d2ff4061";
    //微信AppSecret
    public static final String App_Secret_WX = "0d9f869b459723685769a7d810b5c9a9";

    //签名
    public static final String SIGNATURE="0168e25322b25590f82af0c718ca1812";

    //商户号
    //1379382502
    //api 秘钥
    //yunshikeji963852yunshikeji963852



}
