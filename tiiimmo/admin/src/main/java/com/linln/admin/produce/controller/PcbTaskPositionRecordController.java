package com.linln.admin.produce.controller;


import com.linln.RespAndReqs.PcbTaskReq;
import com.linln.admin.produce.service.PcbTaskPositionRecordService;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/PcbTaskPositionRecord")
public class PcbTaskPositionRecordController {

    @Autowired
    private PcbTaskPositionRecordService pcbTaskPositionRecordService;

    //通过设备号和工序任务号生成记录
    @PostMapping("/buildPositionRecord")
    @ResponseBody
    public ResultVo buildPositionRecord(@RequestBody PcbTaskReq req){
        pcbTaskPositionRecordService.buildPositionRecord(req);
        return ResultVoUtil.success("");
    }



}
