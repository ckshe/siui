package com.linln.admin.pokeYoke.controller;

import com.linln.RespAndReqs.PcbTaskPositionRecordDetailReq;
import com.linln.RespAndReqs.ProcessTaskReq;
import com.linln.admin.produce.domain.PcbTaskPositionRecordDetail;
import com.linln.admin.produce.service.PcbTaskPositionRecordDetailService;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import org.apache.catalina.LifecycleState;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/pokeYoke/product")
public class PcbTaskPositionRecordDetailController {

    @Autowired
    private PcbTaskPositionRecordDetailService pcbTaskPositionRecordDetailService;
    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("pokeYoke:product:index")
    public String index(Model model, PcbTaskPositionRecordDetail pcbTaskPositionRecordDetail) {

        return "/pokeYoke/product/index";
    }

    //获取所有已上料的物料列表
    @PostMapping("/findProductCode")
    @ResponseBody
    public ResultVo findProcessTask(@RequestBody PcbTaskPositionRecordDetailReq req){
        return pcbTaskPositionRecordDetailService.findFinishRecordDetail(req);
    }
}
