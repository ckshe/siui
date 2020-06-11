package com.linln.admin.reports.controller;

import com.linln.admin.produce.domain.PcbTask;
import com.linln.admin.reports.service.ShowBoardService;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ShowBoard")
public class ShowBoardController {


    @Autowired
    private ShowBoardService showBoardService;

    @GetMapping("/pcbTaskBoard")
    @ResponseBody
    public ResultVo pcbTaskBoard(){
        List<PcbTask> pcbTasks = showBoardService.pcbTaskBoard();
        Map<String,Object> mapWeekRate = showBoardService.getPcbTaskThisWeek();
        Map<String, Object> mapProcessWeekRate = showBoardService.getProcessTaskThisWeek();
        List<Map<String,Object>> taskFinishRate = showBoardService.getTaskFinishRate();
        Map<String,Object> map = new HashMap<>();
        map.put("pcbTasks",pcbTasks);
        map.put("mapWeekRate",mapWeekRate);
        map.put("mapProcessWeekRate",mapProcessWeekRate);
        map.put("taskFinishRate",taskFinishRate);

        return ResultVoUtil.success(map);
    }

    @GetMapping("/getDeviceStatus")
    @ResponseBody
    public ResultVo getDeviceStatus(){
        return ResultVoUtil.success(showBoardService.getDeviceStatus());
    }


    @GetMapping("/staffOnBoard")
    @ResponseBody
    public ResultVo staffOnBoard(){
        return ResultVoUtil.success(showBoardService.staffOnBoard());
    }

    @GetMapping("/findByProcessTaskCode/{processTaskCode}")
    @ResponseBody
    public ResultVo findByProcessTaskCode( @PathVariable String processTaskCode){
        return ResultVoUtil.success(showBoardService.findByProcessTaskCode(processTaskCode));
    }




}
