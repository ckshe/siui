package com.linln.admin.base.domain;

import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.StatusUtil;
import lombok.Data;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author lian
 * @date 2020/08/20
 */
@Data
@Entity
@Table(name="produce_process_task_device_theorytime")
@EntityListeners(AuditingEntityListener.class)
@Where(clause = StatusUtil.NOT_DELETE)
public class ProcessTaskDeviceTheorytime implements Serializable {
    // 主键ID
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    // 机台编号
    private String device_code;
    // 物料编号
    private String pcb_code;
    // 工序任务号
    private String process_task_code;
    // 数量
    private Integer amount;
    // 理论工时
    private BigDecimal theory_time;
    // 实际工时
    private String work_time;
    // 开始时间
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date start_time;
    // 创建时间
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date create_time;
    // 数据状态
    private Byte status = StatusEnum.OK.getCode();
}