package com.linln.admin.produce.controller;


import com.linln.RespAndReqs.PcbTaskReq;
import com.linln.admin.produce.domain.PcbTaskPositionRecord;
import com.linln.admin.produce.domain.PcbTaskPositionRecordDetail;
import com.linln.admin.produce.service.PcbTaskPositionRecordService;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/PcbTaskPositionRecord")
public class PcbTaskPositionRecordController {

    @Autowired
    private PcbTaskPositionRecordService pcbTaskPositionRecordService;

    //通过设备号和工序任务号生成记录
    @PostMapping("/buildPositionRecordAndReturn")
    @ResponseBody
    public ResultVo buildPositionRecordAndReturn(@RequestBody PcbTaskReq req){
        PcbTaskPositionRecord record = pcbTaskPositionRecordService.buildPositionRecordAndReturn(req);
        return ResultVoUtil.success(record);
    }


    //通过设备号和工序任务号获取该设备的位置记录
    @PostMapping("/getPositionRecord")
    @ResponseBody
    public ResultVo getPositionRecord(@RequestBody PcbTaskReq req){
        List<PcbTaskPositionRecordDetail> positionRecord = pcbTaskPositionRecordService.getPositionRecord(req);
        return ResultVoUtil.success(positionRecord);
    }


    //通过工序计划号开始record
    @PostMapping("/startPositonRecord")
    @ResponseBody
    public ResultVo startPositonRecord(@RequestBody PcbTaskReq req){
      return pcbTaskPositionRecordService.startPositonRecord(req);
    }

    //通过工序计划号和物料号查询记录detail，并更改状态
    @PostMapping("/scanProductCode")
    @ResponseBody
    public ResultVo scanProductCode(@RequestBody PcbTaskReq req){
        return pcbTaskPositionRecordService.scanProductCode(req);
    }

    //通过工序计划号结束record
    @PostMapping("/finishPositionRecord")
    @ResponseBody
    public ResultVo finishPositionRecord(@RequestBody PcbTaskReq req){
        return pcbTaskPositionRecordService.finishPositionRecord(req);
    }


    //根据工号查询员工信息
    @GetMapping("/getUserInfoByCard/{cardSequence}")
    @ResponseBody
    public ResultVo getUserInfoByCard(@PathVariable String cardSequence){
        return pcbTaskPositionRecordService.getUserInfoByCard( cardSequence);
    }



}
