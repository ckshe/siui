package com.linln.admin.produce.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author 小懒虫
 * @date 2020/05/18
 */
@Data
public class PcbTaskValid implements Serializable {
    @NotEmpty(message = "任务号不能为空")
    private String pcb_task_code;
}