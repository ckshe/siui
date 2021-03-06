package com.linln.admin.base.controller;

import com.linln.admin.base.domain.OperationInstruction;
import com.linln.admin.base.service.OperationInstructionService;
import com.linln.admin.base.util.ApiResponse;
import com.linln.admin.base.validator.OperationInstructionValid;
import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.EntityBeanUtil;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.utils.StatusUtil;
import com.linln.common.vo.ResultVo;
import com.linln.component.shiro.ShiroUtil;
import com.linln.constant.CommonConstant;
import com.linln.utill.FileUtil;
import com.sun.prism.shader.Solid_TextureYV12_AlphaTest_Loader;
import org.apache.commons.io.IOUtils;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author 连
 * @date 2020/06/09
 */
@Controller
@RequestMapping("/base/operationInstruction")
public class OperationInstructionController {

    @Autowired
    private OperationInstructionService operationInstructionService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("base:operationInstruction:index")
    public String index(Model model, OperationInstruction operationInstruction) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", match -> match.contains());

        // 获取数据列表
        Example<OperationInstruction> example = Example.of(operationInstruction, matcher);
        Page<OperationInstruction> list = operationInstructionService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/base/operationInstruction/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("base:operationInstruction:add")
    public String toAdd() {
        return "/base/operationInstruction/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("base:operationInstruction:edit")
    public String toEdit(@PathVariable("id") OperationInstruction operationInstruction, Model model) {
        model.addAttribute("operationInstruction", operationInstruction);
        return "/base/operationInstruction/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"base:operationInstruction:add", "base:operationInstruction:edit"})
    @ResponseBody
    public ResultVo save(@Validated OperationInstructionValid valid, OperationInstruction operationInstruction,
                         @RequestParam("file")MultipartFile file,
                         HttpServletRequest request) {
        // 复制保留无需修改的数据
        if (operationInstruction.getId() != null) {
            OperationInstruction beOperationInstruction = operationInstructionService.getById(operationInstruction.getId());
            EntityBeanUtil.copyProperties(beOperationInstruction, operationInstruction);
        }
        operationInstruction.setUploadTime(new Date());
        operationInstruction.setUserId(ShiroUtil.getSubject().getId());
        operationInstruction.setUploadPeople(ShiroUtil.getSubject().getUsername());
        //operationInstruction.set;
        File nfile = null;
        String path = CommonConstant.file_path+CommonConstant.usebook_path;

        //String path = "D:\\test\\";
        try {
            nfile = FileUtil.multipartFileToFile(file, path);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultVoUtil.error(20001, "保存文件错误，请联系系统管理员");
        }
        //FileUtil.upload(file);
        operationInstruction.setUploadFile(file.getOriginalFilename());

        // 保存数据
        operationInstructionService.save(operationInstruction);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("base:operationInstruction:detail")
    public String toDetail(@PathVariable("id") OperationInstruction operationInstruction, Model model) {
        model.addAttribute("operationInstruction",operationInstruction);
        return "/base/operationInstruction/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("base:operationInstruction:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (operationInstructionService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }

    @PostMapping("/importOperationManual")
    //@RequiresPermissions("operationManual")
    @ResponseBody
    public ApiResponse importOperationManual(@RequestParam("file") MultipartFile file){
        return operationInstructionService.importOperationManual(file);
    }

    /**
     * 在线浏览PDF文件
     * @return
     */
    @RequestMapping("/showPDF")
    @ResponseBody
    public void showPDF(HttpServletResponse response, Long  id)throws IOException, DocumentException {
        operationInstructionService.showPDF(response,id);

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