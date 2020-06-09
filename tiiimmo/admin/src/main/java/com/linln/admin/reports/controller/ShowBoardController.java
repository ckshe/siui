package com.linln.admin.reports.controller;

import com.linln.admin.produce.domain.PcbTask;
import com.linln.admin.reports.service.ShowBoardService;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        Map<String, String> mapProcessWeekRate = showBoardService.getProcessTaskThisWeek();
        Map<String,Object> map = new HashMap<>();
        map.put("pcbTasks",pcbTasks);
        map.put("mapWeekRate",mapWeekRate);
        map.put("mapProcessWeekRate",mapProcessWeekRate);

        return ResultVoUtil.success(map);
    }




}
