package com.linln.admin.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dataBoard")
public class DataBoardController {

    @GetMapping("/dateBoardIndex")
    public String dateBoardIndex(){
        return "";
    }
}
