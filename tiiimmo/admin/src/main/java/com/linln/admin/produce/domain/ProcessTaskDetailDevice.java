package com.linln.admin.produce.domain;

import com.linln.common.enums.StatusEnum;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name ="produce_process_task_detail_device")
public class ProcessTaskDetailDevice {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id ;

    private Byte status = StatusEnum.OK.getCode();

    private String device_code;

    private Integer plan_count;

    private Integer finish_count;

    private String process_task_code;

    private Date plan_day_time;

    private String device_detail_status;

    //工时
    private BigDecimal work_time;

}
