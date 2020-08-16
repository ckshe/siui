package com.linln.admin.device.formModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.linln.admin.device.form.DeviceRegularSafeResForm;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class DeviceRegularSafeEditFormModel {
    @NotNull(message = "Id不能为空")
    private Integer regularSafeId;
    //设备使用地点
    private String devicePlace;
    @JsonFormat(pattern = "yyyy-MM-dd")
    //下次检测日期
    private Date nextSafeTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    //上次检测日期
    private Date lastSafeTime;
    //设备工作环境温度
    private String temperature;
    //检测小组人员
    private String safePerson;
    //设备工作环境温度
    private String humidity;
    //检测辅助工具
    private String safeTool;
    //软件版本及确认
    private String softwareVersion;
    //检测结论
    private String result;
    //设备操作员
    private String deviceOperator;
    //品质监督员
    private String qcOperator;
    //检测监督员
    private String testOperator;
    //车间负责人
    private String amongstPerson;
    private List<DeviceRegularSafeResForm> deviceRegularSafeResForms;
}
