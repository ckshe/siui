package com.linln.admin.circulationTable;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/circulationTable")
public class CirculationTableController {
//    流通表4正面
    @GetMapping("/circulation4")
    public String circulation4(){ return "/circulationTable/circulation4";}
    //    流通表4反面
    @GetMapping("/circulation4_1")
    public String circulation4_1(){ return "/circulationTable/circulation4_1";}
    //    流通表2正面
    @GetMapping("/circulation2")
    public String circulation2(){ return "/circulationTable/circulation2";}
    //    流通表2反面
    @GetMapping("/circulation2_1")
    public String circulation2_1(){ return "/circulationTable/circulation2_1";}
    //    流通表1正面
    @GetMapping("/circulation1")
    public String circulation1(){ return "/circulationTable/circulation1";}
    //    流通表1反面
    @GetMapping("/circulation1_1")
    public String circulation1_1(){ return "/circulationTable/circulation1_1";}
    //    电路板调试流通表正面
    @GetMapping("/f308b")
    public String f308b(){ return "/circulationTable/f308b";}
}
