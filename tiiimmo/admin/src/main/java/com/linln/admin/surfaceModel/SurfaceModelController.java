package com.linln.admin.surfaceModel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/surfaceModel")
public class SurfaceModelController {
    //    G5全自动视觉印刷机维护记录表
    @GetMapping("/f259b")
    public String f259b(){ return "/surfaceModel/f259b";}
    //    G5全自动视觉印刷机维护记录表
    @GetMapping("/f259b_1")
    public String f259b_1(){ return "/surfaceModel/f259b_1";}
    //    贴片机YS12/YS12F日常维护记录表
    @GetMapping("/f260b")
    public String f260b(){ return "/surfaceModel/f260b";}
    @GetMapping("/f260b_1")
    public String f260b_1(){ return "/surfaceModel/f260b_1";}
    @GetMapping("/f260b_2")
    public String f260b_2(){ return "/surfaceModel/f260b_2";}
    //    AOI维护记录表
    @GetMapping("/f261c")
    public String f261c(){ return "/surfaceModel/f261c";}
    //    AOI维护记录表
    @GetMapping("/f261c_1")
    public String f261c_1(){ return "/surfaceModel/f261c_1";}
    //    自动焊接机维护记录表
    @GetMapping("/f273b")
    public String f273b(){ return "/surfaceModel/f273b";}
    //    WS-450Ⅱ波峰焊日常维护记录表
    @GetMapping("/f285a")
    public String f285a(){ return "/surfaceModel/f285a";}
    // Py100A回流焊接机维护记录表
    @GetMapping("/f287a")
    public String f287a(){ return "/surfaceModel/f287a";}

    // 2_F205B（SMT首检记录表）
    @GetMapping("/f205b")
    public String f205b(){ return "/surfaceModel/f205b";}
    @GetMapping("/f205b_1")
    public String f205b_1(){ return "/surfaceModel/f205b_1";}
    //设备使用记录 贴片机
    @GetMapping("/f006c")
    public String f006c(){ return "/surfaceModel/f006c";}
    @GetMapping("/f006c_1")
    public String f006c_1(){ return "/surfaceModel/f006c_1";}
    @GetMapping("/f006c_2")
    public String f006c_2(){ return "/surfaceModel/f006c_2";}

}
