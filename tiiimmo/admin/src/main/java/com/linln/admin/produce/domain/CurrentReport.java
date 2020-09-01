package com.linln.admin.produce.domain;

import com.linln.common.enums.StatusEnum;
import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "produce_current_report")
@Entity
public class CurrentReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //shi否冻结
    private Byte status = StatusEnum.OK.getCode();

    //流通表类型
    private String report_type;

    //排产计划号
    private String pcb_task_code;

    //规格型号
    private String pcb_code;

    //工序计划号
    private String process_task_code;

    //生产批次号
    private String task_sheet_code;

    //json数据
    private String json_data;

}
