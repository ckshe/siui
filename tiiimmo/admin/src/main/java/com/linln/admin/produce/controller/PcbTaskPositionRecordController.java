package com.linln.admin.produce.controller;


import com.linln.RespAndReqs.PcbTaskReq;
import com.linln.admin.produce.domain.PcbTaskPositionRecordDetail;
import com.linln.admin.produce.service.PcbTaskPositionRecordService;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/PcbTaskPositionRecord")
public class PcbTaskPositionRecordController {

    @Autowired
    private PcbTaskPositionRecordService pcbTaskPositionRecordService;

    //通过设备号和排产任务号生成记录
    @PostMapping("/buildPositionRecord")
    @ResponseBody
    public ResultVo buildPositionRecord(@RequestBody PcbTaskReq req){
        pcbTaskPositionRecordService.buildPositionRecord(req);
        return ResultVoUtil.success("");
    }


    //通过设备号和排产任务号获取该设备的位置记录
    @PostMapping("/getPositionRecord")
    @ResponseBody
    public ResultVo getPositionRecord(@RequestBody PcbTaskReq req){
        List<PcbTaskPositionRecordDetail> positionRecord = pcbTaskPositionRecordService.getPositionRecord(req);
        return ResultVoUtil.success(positionRecord);
    }




}
