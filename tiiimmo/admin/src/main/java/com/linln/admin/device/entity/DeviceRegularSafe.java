package com.linln.admin.device.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Proxy(lazy = false)
@DynamicInsert
@DynamicUpdate
@Table(name = "device_regular_safe")
@org.hibernate.annotations.Table(appliesTo = "device_regular_safe", comment = "设备定期维护表")
public class DeviceRegularSafe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer regularSafeId;
    //设备名称
    private String deviceName;
    //设备编号
    private String deviceCode;
    //设备使用地点
    private String devicePlace;
    //本次检测日期
    private Date thisSafeTime;
    //下次检测日期
    private Date nextSafeTime;
    //上次检测日期
    private Date lastSafeTime;
    //设备工作环境温度
    private String temperature;
    //检测小组人员
    private String safePerson;
    //设备工作环境温度
    private String humidity;
    //检测辅助工具
    private String safeTool;
    //软件版本及确认
    private String softwareVersion;
    //检测结论
    private String result;
    //设备操作员
    private String deviceOperator;
    //品质监督员
    private String qcOperator;
    //检测监督员
    private String testOperator;
    //车间负责人
    private String amongstPerson;
}
