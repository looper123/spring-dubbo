package com.itcast.dubbo.web.entity;

import com.itcast.dubbo.ser.annotation.ConvertToChinese;
import com.itcast.dubbo.ser.annotation.SheetHeader;
import com.itcast.dubbo.ser.annotation.TranslateToChinese;

import java.util.Date;
import java.util.List;

/**
 * Created by zebon lu on 2017/5/27.
 */
public class School {
    @SheetHeader(comments="学校名称",index = 0)
    private String name;

    @SheetHeader(comments = "专业",index = 1)
    private String profession;

    @SheetHeader(comments = "竞争力",index = 2)
    private String competition;

    @SheetHeader(comments = "时间",index = 3)
    private Date createTime;

    private short status;

    @SheetHeader(comments = "状态",index = 4)
    @ConvertToChinese(jsonString="{\"0\":\"审核\",\"1\":\"未审核\"}",FieldName = "status")
    private String statusName;

    private List<School>  list;

    public String getStatusName() {
        return statusName;
    }

    @TranslateToChinese(relationShip="{\"0\":\"审核\",\"1\":\"未审核\"}",getMethodName = "getStatus")
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public List<School> getList() {
        return list;
    }

    public void setList(List<School> list) {
        this.list = list;
    }

    public School(String name, String profession, String competition, Date createTime) {
        this.name = name;
        this.profession = profession;
        this.competition = competition;
        this.createTime = createTime;
    }


    public School(String name, String profession, String competition, Date createTime,Short status) {
        this.name = name;
        this.profession = profession;
        this.competition = competition;
        this.createTime = createTime;
        this.status = status;
    }

    public School() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getCompetition() {
        return competition;
    }

    public void setCompetition(String competition) {
        this.competition = competition;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "School{" +
                "name='" + name + '\'' +
                ", profession='" + profession + '\'' +
                ", competition='" + competition + '\'' +
                ", createTime=" + createTime +
                ", status=" + status +
                ", statusName='" + statusName + '\'' +
                ", list=" + list +
                '}';
    }
}
