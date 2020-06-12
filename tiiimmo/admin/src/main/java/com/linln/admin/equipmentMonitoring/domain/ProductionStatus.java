package com.linln.admin.equipmentMonitoring.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.StatusUtil;
import com.linln.modules.system.domain.User;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author 风陵苑主
 * @date 2020/05/21
 */
@Data
@Entity
@Table(name="base_productionStatus")
@EntityListeners(AuditingEntityListener.class)
@Where(clause = StatusUtil.NOT_DELETE)
public class ProductionStatus implements Serializable {
    // 主键ID
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    // 设备编码
    private String deviceCode;
    // 设备名称
    private String deviceName;
    // 所属工序
    private String workingprocess;
    // 制造编码
    private String manufactureCode;
    // PCB编码
    private String pcbCode;
    // PCB名称
    private String pcbName;
    // PCB版本
    private String PCBEdition;
    // 计划完成时间
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date planOverTime;
    // 任务数量
    private String taskNum;
    // 已完成数量
    private String overNum;
    // 当前处理板编号
    private String processingCode;
    // 设备状态
    private String deviceStatus;
    // 合格率
    private String qualifiedRate;
    // 备注
    private String remark;
    // 创建时间
    @CreatedDate
    private Date createDate;
    // 更新时间
    @LastModifiedDate
    private Date updateDate;
    // 创建者
    @CreatedBy
    @ManyToOne(fetch=FetchType.LAZY)
    @NotFound(action=NotFoundAction.IGNORE)
    @JoinColumn(name="create_by")
    @JsonIgnore
    private User createBy;
    // 更新者
    @LastModifiedBy
    @ManyToOne(fetch=FetchType.LAZY)
    @NotFound(action=NotFoundAction.IGNORE)
    @JoinColumn(name="update_by")
    @JsonIgnore
    private User updateBy;
    // 数据状态
    private Byte status = StatusEnum.OK.getCode();
}