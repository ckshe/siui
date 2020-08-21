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

    private String processTaskCode;

    private String pcbCode;

    private String deviceCode;

    private Integer amount;

    private BigDecimal workTime;

    private BigDecimal theoryTime;

    private String utilizationRate;

    private Date createTime;

    private Date startTime;

}
