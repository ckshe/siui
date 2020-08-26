package com.linln.admin.base.controller;

import com.linln.admin.base.domain.ESOP;
import com.linln.admin.base.service.ESOPService;
import com.linln.admin.base.util.ApiResponse;
import com.linln.admin.produce.domain.FileRecord;
import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.EntityBeanUtil;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.utils.StatusUtil;
import com.linln.common.vo.ResultVo;
import com.linln.component.shiro.ShiroUtil;
import com.linln.constant.CommonConstant;
import com.linln.modules.system.domain.User;
import com.linln.utill.FileUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author w
 * @date 2020/06/09
 */
@Controller
@RequestMapping("/base/esop")
public class ESOPController {

    @Autowired
    private ESOPService esopService;



    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("base:esop:index")
    public String index(Model model, ESOP esop) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", match -> match.contains());

        // 获取数据列表
        Example<ESOP> example = Example.of(esop, matcher);
        Page<ESOP> list = esopService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/base/esop/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("base:esop:add")
    public String toAdd() {
        return "/base/esop/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("base:esop:edit")
    public String toEdit(@PathVariable("id") ESOP esop, Model model) {
        model.addAttribute("esop", esop);
        return "/base/esop/add";
    }

    /**
     * 保存添加/修改的数据
     * @param
     */
    @PostMapping("/save")
    @RequiresPermissions({"base:esop:add", "base:esop:edit"})
    @ResponseBody
    public ResultVo save(  ESOP esop,
                         @RequestParam("file")MultipartFile file,
                         HttpServletRequest request) {

        FileRecord fileRecord = new FileRecord();
        fileRecord.setType("新增");
        // 复制保留无需修改的数据
        if (esop.getId() != null) {
            ESOP beesop = esopService.getById(esop.getId());
            EntityBeanUtil.copyProperties(beesop, esop);
            fileRecord.setType("修改");
        }

        esop.setUploadTime(new Date());
        esop.setUserId(ShiroUtil.getSubject().getId());
        esop.setUploadPeople(ShiroUtil.getSubject().getUsername());
        //esop.set;
        File nfile = null;
        String path = CommonConstant.file_path+CommonConstant.esop_path;

        //String path = "D:\\test\\";
        try {
            nfile = FileUtil.multipartFileToFile(file, path);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultVoUtil.error(20001, "保存文件错误，请联系系统管理员");
        }
        //FileUtil.upload(file);
        esop.setUploadFile(file.getOriginalFilename());

        // 保存数据
        esopService.save(esop);

        fileRecord.setPcb_code(esop.getPcbCode());
        fileRecord.setEsop_id(esop.getId());
        fileRecord.setFile_path(esop.getUploadFile());
        fileRecord.setTitle(esop.getName());
        User user = ShiroUtil.getSubject();
        fileRecord.setRecorder_name(user.getNickname());
        esopService.saveFileRecord(fileRecord);
        return ResultVoUtil.SAVE_SUCCESS;
    }
    /**
     * 保存添加/修改的数据
     * @param
     */
    @PostMapping("/save2")
    @RequiresPermissions({"base:esop:add", "base:esop:edit"})
    @ResponseBody
    public ResultVo save2(  ESOP esop,
                           HttpServletRequest request) {
        // 复制保留无需修改的数据
        if (esop.getId() != null) {
            ESOP beesop = esopService.getById(esop.getId());
            EntityBeanUtil.copyProperties(beesop, esop);
        }
        esop.setUploadTime(new Date());
        esop.setUserId(ShiroUtil.getSubject().getId());
        esop.setUploadPeople(ShiroUtil.getSubject().getUsername());

        // 保存数据
        esopService.save(esop);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("base:esop:detail")
    public String toDetail(@PathVariable("id") ESOP esop, Model model) {
        model.addAttribute("esop",esop);
        return "/base/esop/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("base:esop:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (esopService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }

    @PostMapping("/importOperationManual")
    //@RequiresPermissions("operationManual")
    @ResponseBody
    public ApiResponse importOperationManual(@RequestParam("file") MultipartFile file){
        return esopService.importOperationManual(file);
    }

    /**
     * 在线浏览PDF文件
     * @return
     */
    @RequestMapping("/showPDF")
    @ResponseBody
    public void showPDF(HttpServletResponse response, Long  id)throws IOException, DocumentException {
        esopService.showPDF(response,id);

    }

    /*@ResponseBody
    @RequestMapping("/file/upload")
    public ApiResponse upload(@RequestParam("file")MultipartFile file,
                             HttpServletRequest request) {
        String path = "D:\\test";
        String fileName = UUID.randomUUID().toString().replace("-","");
        File tFile = new File(path + File.pathSeparator + fileName);

        FileOutputStream fileOutputStream  = null;
        try {
            fileOutputStream = new FileOutputStream(tFile);
            IOUtils.copy(file.getInputStream(),fileOutputStream);
            System.out.println("上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.ofError("异常");
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ApiResponse.ofSuccess("文件上传成功");
    }*/


        /*if(!file.isEmpty()) {
            String path = "D:/test/";
            path = request.getServletContext().getRealPath("");
            //上传文件名
            String filename=file.getOriginalFilename();
            File filepath=new File(path,filename);
            String fileSuffix = filename.substring(filename.lastIndexOf(".") + 1);
            if (!fileSuffix.equals(".xlsx")){
                return ApiResponse.ofError("文件类型不符合");
            }
            //判断路径是否存在，如果不存在就创建一个
            if (!filepath.getParentFile().exists()){
                filepath.getParentFile().mkdirs();
            }
            //将上传文件保存到一个目标文件当中
            try {
                file.transferTo(new File(path + filename));
                return ApiResponse.ofSuccess("文件上传成功");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else {
            return ApiResponse.ofSuccess("文件为空，请重新上传");
        }
        return ApiResponse.ofError("异常");

    }*/




}