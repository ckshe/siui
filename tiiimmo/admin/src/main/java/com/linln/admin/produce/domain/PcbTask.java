package com.linln.admin.produce.domain;

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
 * @author 小懒虫
 * @date 2020/05/18
 */
@Data
@Entity
@Table(name="produce_pcb_task")
@EntityListeners(AuditingEntityListener.class)
@Where(clause = StatusUtil.NOT_DELETE)
public class PcbTask implements Serializable {
    // 主键ID
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    // 任务号
    private String pcb_task_code;
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
    // 制造编号
    private String task_sheet_code;
    // 通知日期
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date task_sheet_date;
    // 厂区
    private String factory;
    // 车间
    private String workshop;
    // 机型编码
    private String model_id;
    // 机型名称
    private String model_name;
    // 机型型号
    private String model_ver;
    // RoHS
    private String is_rohs;
    // 生产数量
    private Integer quantity;
    // 计划完成时间
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date plan_complete_date;
    // PCB编码
    private String pcb_id;
    // PCB名称
    private String pcb_name;
    // PCB数量
    private Integer pcb_quantity;
    // 板编号
    private String batch_id;
    // PCB光板号
    private String pcb_plate_id;
    // PCB修改标记
    private String pcb_modify_tag;
    // PCB修改时间
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date pcb_update_time;
    // AB面
    private Byte pcb_is_ab;
    // 贴片领料
    private Byte patch_pick;

    //完成数量
    private Integer amount_completed;
    // 工单状态
    private String pcb_task_status;
    //  计划开始时间
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date produce_plan_date;
    // 实际开始时间
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date produce_date;
    // 计划完成时间
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date produce_plan_complete_date;
    // 实际完成时间
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date produce_complete_date;
    //投料单号
    private String feeding_task_code;

    //优先级
    private Integer priority;

    //没卵用的状态，只用来筛选前端显示ERP排产计划
    private String temp_status_useless;

    //齐套率
    private String qi_tao_lv;
}