package com.linln.admin.reports.controller;

import com.linln.RespAndReqs.PcbTaskReq;
import com.linln.admin.reports.request.BadDetailReq;
import com.linln.admin.reports.request.ClassInfoReq;
import com.linln.admin.reports.request.CurrentReportReq;
import com.linln.admin.reports.service.ReportService;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    //设备工序计划
    @PostMapping("/badDetailReport")
    @ResponseBody
    public ResultVo findProcessTaskByDevice(@RequestBody BadDetailReq req){
        return reportService.badDetailReport(req);
    }


    //通用报表查询
    @PostMapping("/findCurrentReport")
    @ResponseBody
    public ResultVo findCurrentReport(@RequestBody CurrentReportReq req){
        return reportService.findCurrentReport(req);
    }

    /**
     * 班次分析表页面
     */
    @GetMapping("/toFindClassInfo")
    @RequiresPermissions("report:classReportReport")
    public String dateReportForm() {
        return "/quality/classReportReportForm";
    }


    //班次分析
    @PostMapping("/findClassInfo")
    @ResponseBody
    public ResultVo findClassInfo(@RequestBody ClassInfoReq req){
        return reportService.findClassInfo(req);
    }

}
