package com.linln.admin.system.controller;

import com.linln.modules.system.service.DictService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * @author 小懒虫
 * @date 2018/8/14
 */
@Controller
@RequestMapping("/windowPad")
public class windowPadController {

    @Autowired
    private DictService dictService;

    @GetMapping("/index")
    public String index(){
        return "/windowPad/index";
    }

    @GetMapping("/productInfo")
    public String productInfo(){
        return "/windowPad/productInfo";
    }

    @GetMapping("/badAdd")
    public String badAdd(){ return "/windowPad/badAdd";}

    @GetMapping("/scancalc")
    public String scancalc(){return "/windowPad/scancalc/scancalc";}

    //  系统设置
    @GetMapping("/systemSet")
    public String systemSet(){
        return "/windowPad/systemSet/systemSet";
    }

    //    设备参数
    @GetMapping("/circulation")
    public String device(){ return "/windowPad/circulation";}

    //    首检记录
    @GetMapping("/firstQuality")
    public String firstQuality(){ return "/windowPad/firstQuality";}

    //    环境记录
    @GetMapping("/ambient")
    public String ambient(){ return "/windowPad/ambient";}

    //  物料明细
    @GetMapping("/materiel")
    public String materiel(){ return "/windowPad/materiel"; }

    // 显示PDF
    @GetMapping("/showPDF")
    public String showPDF(){
        return "/windowPad/showPDF";
    }

    // 显示操作指导
    @GetMapping("/operationPDF")
    public String operationPDF(){
        return "/windowPad/operationPDF";
    }

    //    历史记录
    @GetMapping("/history")
    public String history(){
        return "/windowPad/history";
    }

    //    设备维护
    @GetMapping("/maintenance")
    public String maintenance(){
        return "/windowPad/maintenance";
    }

}
