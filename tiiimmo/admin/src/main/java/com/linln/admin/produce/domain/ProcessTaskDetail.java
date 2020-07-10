package com.linln.admin.produce.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name ="produce_process_task_detail")
public class ProcessTaskDetail {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id ;

    private Byte status;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date plan_day_time;

    private String process_task_code;

    private Integer plan_count;

    private Integer finish_count;

    private String detail_type;

    private String user_name;

    private String process_name;
}
