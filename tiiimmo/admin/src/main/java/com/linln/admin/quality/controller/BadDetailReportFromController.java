package com.linln.admin.quality.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 小懒虫
 * @date 2020/05/18
 */
@Controller
@RequestMapping("/report")
public class BadDetailReportFromController {

   /**
     * 工序计划列表页面
     */
    @GetMapping("/badDetailReport")
    ///report/badDetailReport
    @RequiresPermissions("report:badDetailReport")
    public String dateReportForm() {
        return "/quality/badDetailReportForm";
    }
}
