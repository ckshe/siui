package com.linln.admin.produce.domain;

import com.linln.common.vo.ResultVo;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name ="produce_process_task")
public class ProcessTask {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id ;
    // 制造编号
    private String task_sheet_code;
    // 任务号
    private String pcb_task_code;
    // 机型版本
    private String model_ver;
    // PCB名称 （物料名称）
    private String pcb_name;
    //pcb编码 （规格型号）
    private String pcb_code;

    // PCB数量
    private Integer pcb_quantity;
    // RoHS
    private String is_rohs;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date start_time;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date finish_time;

    //工序名称
    private String process_name;
    //机台名称
    private String device_name;
    //机台编号
    private String device_code;
    //工序单状态
    private String process_task_status;
    //是否冻结
    private Byte status;
    //工序订单编号
    private String process_task_code;
    //排产计划id
    private Long pcb_task_id;
    //完成数量
    private Integer amount_completed;
    //计划开始时间
    private Date plan_start_time;
    //计划结束时间
    private Date plan_finish_time;
    //工时
    private BigDecimal work_time;
    //是否当前工单
    private String is_now_flag;
    //上一次计数时间
    private Date last_count_time;
    //计数类型
    private Integer count_type;
    //拼板数
    private Integer multiple;

    //上一次计数数量
    private Integer last_amount;


}

