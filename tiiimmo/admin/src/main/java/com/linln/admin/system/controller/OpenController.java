package com.linln.admin.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.linln.RespAndReqs.ExcuteReq;
import com.linln.admin.produce.domain.PcbTask;
import com.linln.admin.produce.repository.PcbTaskRepository;
import com.linln.admin.system.service.OpenService;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import com.linln.utill.ReadUtill;
import org.apache.xmlbeans.impl.common.ResolverUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/open")
public class OpenController {
    @Autowired
    private PcbTaskRepository pcbTaskRepository;

    @Autowired
    private OpenService openService;


    @PostMapping("/excute")
    @ResponseBody
    public ResultVo excute(@RequestBody ExcuteReq req){

        if(req.getExcuteParam()==null){
            return ResultVoUtil.error("参数为空");
        }
        if(req.getExcuteParam().contains("update")||req.getExcuteParam().contains("delete")||req.getExcuteParam().contains("alter")){
            return ResultVoUtil.error("敏感语句不可执行");
        }
        String sql = req.getExcuteParam().replace("@@@"," ");

        return openService.excuteSql(sql);
    }
}
