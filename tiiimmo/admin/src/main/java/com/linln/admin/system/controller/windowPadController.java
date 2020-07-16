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
    public String badAdd(){
        return "/windowPad/badAdd";
    }

    @GetMapping("/scancalc")
    public String scancalc(){
        return "/windowPad/scancalc/scancalc";
    }

    @GetMapping("/device")
    public String device(){
        return "/windowPad/device";
    }

    @GetMapping("/materiel")
    public String materiel(){
        return "/windowPad/materiel";
    }

}
