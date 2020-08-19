package com.linln.admin.produce.controller;

import com.linln.admin.produce.domain.CraftParameterRecord;
import com.linln.admin.produce.request.CraftParameterRecordReq;
import com.linln.admin.produce.request.CraftParameterReq;
import com.linln.admin.produce.service.CraftParameterRecordService;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 流通表
 */
@Controller
@RequestMapping("/circulate")
public class CirculateController {

    @Autowired
    private CraftParameterRecordService craftParameterRecordService;

    //添加设备工艺参数
    @PostMapping("/addCraftParameterRecord")
    @ResponseBody
    public ResultVo addCraftParameterRecord(@RequestBody CraftParameterRecord record){

         craftParameterRecordService.addCraftParameterRecord(record);
         return ResultVoUtil.success("添加成功");
    }

    //查询设备工艺参数
    @PostMapping("/findDeviceCraftParamByDeviceCode")
    @ResponseBody
    public ResultVo findDeviceCraftParamByDeviceCode(@RequestBody CraftParameterReq  req){

        return craftParameterRecordService.findDeviceCraftParamByDeviceCode(req.getDeviceCode());
     }

     //查询流通表的设备工序工艺参数记录
    @PostMapping("/findCraftParameterRecordByProcessTaskCode")
    @ResponseBody
    public ResultVo findCraftParameterRecordByProcessTaskCode(@RequestBody CraftParameterRecordReq req){
        return craftParameterRecordService.findCraftParameterRecordByProcessTaskCode(req.getProcessTaskCode());
    }

    //流通表的设备工序工艺参数记录质检
    @PostMapping("/qcCraftParameterRecord")
    @ResponseBody
    public ResultVo qcCraftParameterRecord(@RequestBody CraftParameterRecordReq req){
        return craftParameterRecordService.qcCraftParameterRecord(req);
    }

}
