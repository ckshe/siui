package com.linln.admin.maintenance.controller;

import com.linln.admin.maintenance.domain.MaintenanceTable;
import com.linln.admin.maintenance.service.MaintenanceTableService;
import com.linln.admin.maintenance.validator.MaintenanceTableValid;
import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.EntityBeanUtil;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.utils.StatusUtil;
import com.linln.common.vo.ResultVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 风陵苑主
 * @date 2020/05/21
 */
@Controller
@RequestMapping("/maintenance")
public class MaintenanceAmbientController {
    @GetMapping("/deviceAmbient")
    ///report/badDetailReport/maintenance/deviceAmbient
    @RequiresPermissions("maintenance:deviceAmbient")
    public String dateReportForm() {
        return "/maintenance/deviceAmbient/index";
    }
//    设备记录表模
    @GetMapping("/deviceRecord")
    ///report/badDetailReport/maintenance/deviceAmbient
    @RequiresPermissions("maintenance:deviceRecord")
    public String deviceRecord() {
        return "/maintenance/deviceRecord/index";
    }
}