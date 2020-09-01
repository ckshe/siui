package com.linln.admin.produce.controller;

import com.linln.admin.produce.domain.CurrentReport;
import com.linln.admin.produce.service.CurrentReportService;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/currentReport")
public class CurrentReportController {

    @Autowired
    private CurrentReportService currentReportService;

    //新增报表
    @PostMapping("/addCurrentReport")
    @ResponseBody
    public ResultVo addCurrentReport(@RequestBody CurrentReport currentReport){
        currentReportService.addCurrentReport(currentReport);
        return ResultVoUtil.success("保存成功");

    }
}
