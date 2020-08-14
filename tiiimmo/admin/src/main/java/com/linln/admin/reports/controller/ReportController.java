package com.linln.admin.reports.controller;

import com.linln.RespAndReqs.PcbTaskReq;
import com.linln.admin.reports.request.BadDetailReq;
import com.linln.admin.reports.service.ReportService;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    //设备工序计划
    @PostMapping("/badDetailReport")
    @ResponseBody
    public ResultVo findProcessTaskByDevice(@RequestBody BadDetailReq req){
        return ResultVoUtil.success(reportService.badDetailReport(req));}

}
