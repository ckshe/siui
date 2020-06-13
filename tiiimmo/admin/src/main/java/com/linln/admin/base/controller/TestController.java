package com.linln.admin.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author www
 * @date 2020/05/14
 */
@Controller
@RequestMapping("/base/test")
public class TestController {



    /**
     * 列表页面
     */
    @GetMapping("/index")

    public String index(Model model) {


        return "/base/test/index";
    }


}