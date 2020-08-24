package com.linln.admin.device.form;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
@Data
public class DeviceUseHistoryEditForm {
    @NotNull(message = "id不能为空")
    private Integer deviceHistoryId;
    //开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date useStartTime;
    //结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date useEndTime;
    //使用人
    private String usePerson;
    //使用时间
    private Integer useTime;
    //生产数量
    private Integer amount;
    //使用情况
    private String useResult;
    //备注
    private String remark;
}
