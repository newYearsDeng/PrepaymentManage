package com.northmeter.prepaymentmanage.model.EventBus;

/**
 * Created by Lht
 * on 2016/12/6.
 * des:
 */
public class ChooseInfo {
    public String  time;
    public String  buildingID;
    public String  dateType;
    public String positioin;


    public ChooseInfo(String dateType,String time, String buildingID,String positioin) {
        this.dateType=dateType;
        this.time = time;
        this.buildingID = buildingID;
        this.positioin=positioin;
    }

    @Override
    public String toString() {
        return "ChooseInfo{" +
                "time='" + time + '\'' +
                ", buildingID='" + buildingID + '\'' +
                ", dateType='" + dateType + '\'' +
                '}';
    }
}
