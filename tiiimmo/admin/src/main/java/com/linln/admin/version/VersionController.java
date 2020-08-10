package com.linln.admin.version;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author 小懒虫
 * @date 2018/12/02
 */
@Controller
@RequestMapping("/version")
public class VersionController {
    @GetMapping("/index")
    public String scancalc(){return "/version/index";}
}
