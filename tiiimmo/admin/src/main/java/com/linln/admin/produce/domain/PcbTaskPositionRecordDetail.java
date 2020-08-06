package com.linln.admin.produce.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name ="produce_task_position_record_detail")
public class PcbTaskPositionRecordDetail {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id ;
    private Byte status;
    //记录id
    private Long record_id;
    //设备号
    private String device_code;
    //排产任务号
    private String pcb_task_code;
    //工序任务号
    private String process_task_code;
    //元件名
    private String element_name;
    // 物料编号
    private String product_code;
    // 安装位置
    private String position;
    // 安装状态 0无1位置确认2已插入
    private String install_status;
    //原物料编号
    private String last_product_code;


}
