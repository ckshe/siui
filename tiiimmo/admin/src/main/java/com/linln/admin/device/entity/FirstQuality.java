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
@Table(name = "first_quality")
@org.hibernate.annotations.Table(appliesTo = "first_quality", comment = "首检记录表")
public class FirstQuality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer qualityId;
    //生产任务单号
    private String pcbTaskCode;
    //批次
    private String taskSheetCode;
    //规格型号
    private String pcbCode;
    //环节
    private String segment;
    //一次首检
    private String qualityOne;
    //二次首检
    private String qualityTwo;
    //三次首检
    private String qualityThree;
    //不合格原因
    private String badResult;
    //检查人
    private String qualityPerson;
    //检查日期
    private Date qualityDate;
    //备注
    private String qualityRemark;
}
