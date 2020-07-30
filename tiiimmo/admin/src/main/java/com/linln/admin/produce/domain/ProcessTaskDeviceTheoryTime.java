package com.linln.admin.produce.domain;

import com.linln.common.enums.StatusEnum;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name ="produce_process_task_device_theorytime")
public class ProcessTaskDeviceTheoryTime {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Byte status = StatusEnum.OK.getCode();

    private String process_task_code;

    private String pcb_code;

    private String device_code;

    private Integer amount;

    private BigDecimal work_time;

    private BigDecimal theory_time;

    private String utilization_rate;

    private Date create_time;

}
