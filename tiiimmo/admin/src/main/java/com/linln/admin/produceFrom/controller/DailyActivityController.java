package com.linln.admin.produceFrom.controller;

import com.linln.RespAndReqs.PcbTaskPositionRecordDetailReq;
import com.linln.RespAndReqs.UserDeviceHistoryReq;
import com.linln.admin.produce.repository.UserDeviceHistoryRepository;
import com.linln.admin.produceFrom.service.UserDeviceHistoryService;
import com.linln.common.vo.ResultVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/produceFrom")
public class DailyActivityController {

    @Autowired
    private UserDeviceHistoryService userDeviceHistoryService;

    /**
     * 人员日活量统计列表页面
     */
    @GetMapping("/dailyActivity")
    @RequiresPermissions("produceFrom:dailyActivity")
    public String processTask() {

        return "/produceFrom/dailyActivity";
    }

    //获取所有已上料的物料列表
    @PostMapping("/findDeviceHistory")
    @ResponseBody
    public ResultVo findDeviceHistory(@RequestBody UserDeviceHistoryReq req){
        return userDeviceHistoryService.findDeviceHistory(req);
    }



}
