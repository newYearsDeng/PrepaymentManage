package com.northmeter.prepaymentmanage.model.EventBus;

/**
  *
  *@author lht
  *@time   2016/11/10 10:28
  *@des        已经选择完建筑
  */
public class AlreadyBuilding {
    public String Area_ID;
    //校区
    public String school;
    //建筑
    public String building;
    //楼层
    public String floor;

    public String room;

    public AlreadyBuilding() {
    }

    public AlreadyBuilding(String area_ID, String school, String building, String floor, String room) {
        Area_ID = area_ID;
        this.school = school;
        this.building = building;
        this.floor = floor;
        this.room = room;
    }

    @Override
    public String toString() {
        return "AlreadyBuilding{" +
                "Area_ID='" + Area_ID + '\'' +
                ", school='" + school + '\'' +
                ", building='" + building + '\'' +
                ", floor='" + floor + '\'' +
                ", room='" + room + '\'' +
                '}';
    }
}
