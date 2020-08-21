package com.linln.admin.device.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Entity
@Data
@Proxy(lazy = false)
@DynamicInsert
@DynamicUpdate
@Table(name = "quality_type")
@org.hibernate.annotations.Table(appliesTo = "quality_type", comment = "首检环节表")
public class QualityType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer qualityTypeId;
    //环节名称
    private String qualityTypeName;
}
