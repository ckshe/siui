package com.linln.admin.maintenance.validator;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotNull;

/**
 * @author 小懒虫
 * @date 2020/08/19
 */
@Data
public class SpotCheckValid implements Serializable {
    @NotNull(message = "点检日期不能为空")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date checkData;
}