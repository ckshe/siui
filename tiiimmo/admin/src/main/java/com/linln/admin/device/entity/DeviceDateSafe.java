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
@Table(name = "device_date_safe")
@org.hibernate.annotations.Table(appliesTo = "device_date_safe", comment = "设备日常维护表")
public class DeviceDateSafe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //维护日期
    private Date safeTime;
    //维护类型
    private String safeType;
    //维护设备
    private String safeDeviceCode;
    //维护内容
    private String safeContent;
    //维护结果
    private String safeResult;
    //维护人
    private String safePerson;
}
