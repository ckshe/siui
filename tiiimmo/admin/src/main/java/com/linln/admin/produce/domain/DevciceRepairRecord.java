package com.linln.admin.produce.domain;

import com.linln.common.enums.StatusEnum;
import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "produce_device_repair_report")
@Entity
public class DevciceRepairRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //shi否冻结
    private Byte status = StatusEnum.OK.getCode();

    //年份
    private String year;

    //月份
    private String month;

    //设备
    private String device_code;

    //年月
    private String year_month;
}
