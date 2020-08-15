package com.linln.admin.produceFrom.controller;

import com.linln.RespAndReqs.PcbTaskReq;
import com.linln.RespAndReqs.ProcessTaskReq;
import com.linln.RespAndReqs.responce.PlateNoInfo;
import com.linln.admin.produce.domain.PcbTask;
import com.linln.admin.produce.domain.ProcessTask;
import com.linln.admin.produce.domain.ProcessTaskDetail;
import com.linln.admin.produce.service.PcbTaskService;
import com.linln.admin.produce.service.ProcessTaskService;
import com.linln.admin.produce.validator.PcbTaskValid;
import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.EntityBeanUtil;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.utils.StatusUtil;
import com.linln.common.vo.ResultVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 小懒虫
 * @date 2020/05/18
 */
@Controller
@RequestMapping("/produceFrom")
public class PcbTaskReportFromController {

   /**
     * 生产进度报表页面
     */
    @GetMapping("/dateReportForm")
    @RequiresPermissions("produceFrom:dateReportForm")
    public String processTask() {return "/produceFrom/dateReportForm";}

    /**
     * 生产流通表页面
     */
    @GetMapping("/circulationFrom")
    @RequiresPermissions("produceFrom:circulationFrom")
    public String circulation() {return "/produceFrom/circulationFrom";}

    /**
     * 生产流通表页面
     */
    @GetMapping("/circulationDetail")
    public String circulationDetail() {return "/produceFrom/circulationDetail";}
}
