package com.linln.admin.useRecord;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/useRecord")
public class UseRecordController {
    // 丝印机使用记录表
    @GetMapping("/f005c")
    public String f005c(){ return "/useRecord/f005c";}
    // 贴片机使用记录表
    @GetMapping("/f006c")
    public String f006c(){ return "/useRecord/f006c";}
    // 回流焊接机使用记录表
    @GetMapping("/f086c")
    public String f086c(){ return "/useRecord/f086c";}
    // AOI使用记录表
    @GetMapping("/f262a")
    public String f262a(){ return "/useRecord/f262a";}
    // 自动焊接机使用记录表
    @GetMapping("/f275a")
    public String f275a(){ return "/useRecord/f275a";}
    // 波峰焊使用记录表
    @GetMapping("/f286a")
    public String f286a(){ return "/useRecord/f286a";}
}
