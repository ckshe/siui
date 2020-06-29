package com.linln.admin.produce.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name ="produce_task_position_record")
public class PcbTaskPositionRecord {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id ;
    private Byte status;
    //设备号
    private String device_code;
    //排产任务号
    private String pcb_task_code;
    //开始时间
    private Date start_time;
    //结束时间
    private Date finish_time;
    //记录状态 0未开始1开始2结束
    private String record_status;

    //工序任务号
    private String process_task_code;

    private String user_name;
    @Transient
    List<PcbTaskPositionRecordDetail> detailList;
}
