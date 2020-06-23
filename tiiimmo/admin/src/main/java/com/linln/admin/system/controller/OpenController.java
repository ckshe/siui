package com.linln.admin.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.linln.RespAndReqs.ExcuteReq;
import com.linln.RespAndReqs.PcbTaskReq;
import com.linln.admin.base.domain.Device;
import com.linln.admin.base.repository.DeviceRepository;
import com.linln.admin.produce.domain.PcbTask;
import com.linln.admin.produce.repository.PcbTaskRepository;
import com.linln.admin.produce.service.PcbTaskService;
import com.linln.admin.system.service.OpenService;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import com.linln.utill.ReadUtill;
import org.apache.commons.io.IOUtils;
import org.apache.xmlbeans.impl.common.ResolverUtil;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@RestController
@RequestMapping("/open")
public class OpenController {
    @Autowired
    private PcbTaskRepository pcbTaskRepository;

    @Autowired
    private OpenService openService;

    @Autowired
    private PcbTaskService pcbTaskService;

    @Autowired
    private DeviceRepository deviceRepository;


    @PostMapping("/excute")
    @ResponseBody
    public ResultVo excute(@RequestBody ExcuteReq req){

        if(req.getExcuteParam()==null){
            return ResultVoUtil.error("参数为空");
        }
        if(req.getExcuteParam().contains("update")||req.getExcuteParam().contains("delete")||req.getExcuteParam().contains("alter")){
           // return ResultVoUtil.error("敏感语句不可执行");
        }
        String sql = req.getExcuteParam().replace("@@@"," ");

        return openService.excuteSql(sql);
    }


    //设备接口
    @PostMapping("/deviceProduceAmount")
    @ResponseBody
    public Map<String,Object> deviceProduceAmount(String data){
        System.out.println(data);
        List<PcbTaskReq> req = (List<PcbTaskReq> ) JSON.parseArray(data,PcbTaskReq.class);
        return pcbTaskService.deviceProduceAmount(req.get(0));
    }


    //轮询获取卡位信息
    @PostMapping("/checkPositionSort")
    @ResponseBody
    public Map<String,Object> checkPositionSort(String data){
        System.out.println(data);
        List<PcbTaskReq> req = (List<PcbTaskReq> ) JSON.parseArray(data,PcbTaskReq.class);
        return pcbTaskService.checkPositionSort(req.get(0));
    }

    //返回插入成功
    @PostMapping("/noticePutinStatus")
    @ResponseBody
    public Map<String,Object> noticePutinStatus(String data){
        System.out.println(data);
        List<PcbTaskReq> req = (List<PcbTaskReq> ) JSON.parseArray(data,PcbTaskReq.class);
        return pcbTaskService.noticePutinStatus(req.get(0));
    }


    //扫码计数接口
    @PostMapping("/scanCountPlate")
    @ResponseBody
    public Map<String,Object> scanCountPlate(String data){
        System.out.println(data);
        List<PcbTaskReq> req = (List<PcbTaskReq> ) JSON.parseArray(data,PcbTaskReq.class);
        return pcbTaskService.scanCountPlate(req.get(0));
    }


    //临时修改任务单数据
    @PostMapping("/tempChangeTaskrocess")
    @ResponseBody
    public ResultVo tempChangeTaskrocess(@RequestBody PcbTaskReq pcbTaskReq){
        return pcbTaskService.tempChangeTaskrocess(pcbTaskReq);
    }

    /**
     * 在线浏览PDF文件
     * @return
     */
    @RequestMapping("/showPDF")
    @ResponseBody
    public void showPDF(HttpServletResponse response, String  deviceCode)throws IOException, DocumentException {
        //需要填充的数据
        Map<String, Object> data = new HashMap<>(16);
        data.put("name", "kevin");
        // 读取pdf并预览
        readPDF(response,deviceCode);
    }

    /**
     * 读取本地pdf,这里设置的是预览
     */
    private void readPDF(HttpServletResponse response,String  deviceCode) {
        Device device= deviceRepository.findbyDeviceCode(deviceCode);
        response.reset();
        response.setContentType("application/pdf");
        try {
            File file = new File("C:\\chaosheng_file\\" + device.getUse_book()+".pdf");
            FileInputStream fileInputStream = new FileInputStream(file);
            OutputStream outputStream = response.getOutputStream();
            IOUtils.write(IOUtils.toByteArray(fileInputStream), outputStream);
            response.setHeader("Content-Disposition","inline; filename= file");
            outputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
