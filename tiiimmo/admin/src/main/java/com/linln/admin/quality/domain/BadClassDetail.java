package com.linln.admin.quality.domain;

import com.linln.common.enums.StatusEnum;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="quality_badclass_detail")
public class BadClassDetail {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    // 数据状态
    private Byte status = StatusEnum.OK.getCode();

    private String pcb_task_code;

    private String bad_type;

    private String plate_no;

    private Date record_time;

    private String recorder_name;

}