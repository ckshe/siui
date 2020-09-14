package com.linln.admin.useRecord;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/useRecord")
public class UseRecordController {
    @GetMapping("/f005c")
    public String f005c(){ return "/useRecord/f005c";}
    @GetMapping("/f006c")
    public String f006c(){ return "/useRecord/f006c";}
    @GetMapping("/f086c")
    public String f086c(){ return "/useRecord/f086c";}
    @GetMapping("/f262a")
    public String f262a(){ return "/useRecord/f262a";}
    @GetMapping("/f275a")
    public String f275a(){ return "/useRecord/f275a";}
    @GetMapping("/f286a")
    public String f286a(){ return "/useRecord/f286a";}
}
