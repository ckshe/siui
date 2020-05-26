package com.linln.admin.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.linln.admin.produce.domain.PcbTask;
import com.linln.admin.produce.repository.PcbTaskRepository;
import com.linln.common.vo.ResultVo;
import com.linln.utill.ReadUtill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/open")
public class OpenController {
    @Autowired
    private PcbTaskRepository pcbTaskRepository;

    @GetMapping("/sysnPcbTask")
    @ResponseBody
    public ResultVo PCBCurrency(){
        String path = "D:\\workspace\\timosecond\\tiiimmo\\admin\\src\\main\\resources\\task.json";

        String s = ReadUtill.readJsonFile(path);
        JSONObject jobj = JSON.parseObject(s);
        JSONArray lists = jobj.getJSONArray("data");
        List<PcbTask> pckTaskList = new ArrayList<>();
        for(int i = 0 ; i<lists.size();i++){
            JSONObject param = lists.getJSONObject(i);
            String task_sheet_id = param.getString("制造编号");
            Date produce_plan_date =  param.getDate("计划投产时间");
            String pcb_task_id = param.getString("任务号");
            String pcb_name = param.getString("pcb_name");
            Date produce_date =  param.getDate("实际投产时间");
            Date produce_plan_complete_date =  param.getDate("计划完成时间");
            Date produce_complete_date =  param.getDate("生产完成时间");
            String model_name = param.getString("机型名称");
            String pcb_id = param.getString("PCB编码");
            String pcb_task_status = param.getString("工单状态");
            String model_ver = param.getString("机型版本");
            Integer pcb_quantity = param.getInteger("PCB数量");
            String feeding_code = param.getString("投料单号");
            String workshop = param.getString("车间");
            PcbTask pcbTask = new PcbTask();
            pcbTask.setTask_sheet_id(task_sheet_id);
            pcbTask.setProduce_plan_date(produce_plan_date);
            pcbTask.setPcb_task_id(pcb_task_id);
            pcbTask.setPcb_name(pcb_name);
            pcbTask.setProduce_date(produce_date);
            pcbTask.setProduce_plan_complete_date(produce_plan_complete_date);
            pcbTask.setProduce_complete_date(produce_complete_date);
            pcbTask.setModel_name(model_name);
            pcbTask.setPcb_id(pcb_id);
            pcbTask.setPcb_task_status(pcb_task_status);
            pcbTask.setModel_ver(model_ver);
            pcbTask.setPcb_quantity(pcb_quantity);
            pcbTask.setFeeding_code(feeding_code);
            pcbTask.setWorkshop(workshop);
            pckTaskList.add(pcbTask);
        }
        pcbTaskRepository.saveAll(pckTaskList);

        return null;
    }
}
