package com.linln.admin.device.form;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class FirstQualityEditForm {
    @NotNull(message = "Id不能为空")
    private Integer qualityId;
    //生产任务单号
    private String pcbTaskCode;
    //批次
    private String taskSheetCode;
    //规格型号
    private String pcbCode;
    //环节
    private String segment;
    //一次首检
    private String qualityOne;
    //二次首检
    private String qualityTwo;
    //三次首检
    private String qualityThree;
    //不合格原因
    private String badResult;
    //检查人
    private String qualityPerson;
    //检查日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date qualityDate;
    //备注
    private String qualityRemark;
}
