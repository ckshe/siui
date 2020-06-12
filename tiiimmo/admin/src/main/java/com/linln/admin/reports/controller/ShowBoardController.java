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


    //生产计划/周任务达成率/产线任务达成率/各批次完成率
    @GetMapping("/pcbTaskBoard")
    @ResponseBody
    public ResultVo pcbTaskBoard(){
        List<PcbTask> pcbTasks = showBoardService.pcbTaskBoard();
        Map<String,Object> mapWeekRate = showBoardService.getMapWeekRate();
        Map<String, Object> mapProcessWeekRate = showBoardService.getMapProcessWeekRate();
        List<Map<String,Object>> taskFinishRate = showBoardService.getTaskFinishRate();
        Map<String,Object> map = new HashMap<>();
        map.put("pcbTasks",pcbTasks);
        map.put("mapWeekRate",mapWeekRate);
        map.put("mapProcessWeekRate",mapProcessWeekRate);
        map.put("taskFinishRate",taskFinishRate);

        return ResultVoUtil.success(map);
    }


    //设备启停信号接口
    @GetMapping("/getDeviceStatus")
    @ResponseBody
    public ResultVo getDeviceStatus(){
        return ResultVoUtil.success(showBoardService.getDeviceStatus());
    }


    //人员上机记录
    @GetMapping("/staffOnBoard")
    @ResponseBody
    public ResultVo staffOnBoard(){
        return ResultVoUtil.success(showBoardService.staffOnBoard());
    }

    //工序计划详情
    @GetMapping("/findByProcessTaskCode/{processTaskCode}")
    @ResponseBody
    public ResultVo findByProcessTaskCode( @PathVariable String processTaskCode){
        return ResultVoUtil.success(showBoardService.findByProcessTaskCode(processTaskCode));
    }

    //日计划看板生产计划
    @GetMapping("/findProcessTaskByDate")
    @ResponseBody
    public ResultVo findProcessTaskByDate(){
        return ResultVoUtil.success(showBoardService.findProcessTaskByDate());
    }


    //日计划日任务达成率&周任务完成数量
    @GetMapping("/getMapProcessThisWeekRate")
    @ResponseBody
    public ResultVo getMapProcessThisWeekRate(){
        return ResultVoUtil.success(showBoardService.getMapProcessThisWeekRate());
    }

    //产线日任务达成率
    @GetMapping("/getMapProcessDayRate")
    @ResponseBody
    public ResultVo getMapProcessDayRate(){
        return ResultVoUtil.success(showBoardService.getMapProcessDayRate());
    }


    //生产进度统计
    @GetMapping("/getMapProcessTypeDayRate")
    @ResponseBody
    public ResultVo getMapProcessTypeDayRate(){
        return ResultVoUtil.success(showBoardService.getMapProcessTypeDayRate());
    }




}
