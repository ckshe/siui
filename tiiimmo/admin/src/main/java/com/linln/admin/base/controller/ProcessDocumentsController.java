package com.linln.admin.base.controller;

import com.linln.admin.base.domain.ProcessDocuments;
import com.linln.admin.base.domain.UserSignature;
import com.linln.admin.base.service.ProcessDocumentsService;
import com.linln.admin.base.validator.ProcessDocumentsValid;
import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.EntityBeanUtil;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.utils.StatusUtil;
import com.linln.common.vo.ResultVo;
import com.linln.constant.CommonConstant;
import com.linln.utill.FileUtil;
import com.spire.data.table.DataException;
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
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author www
 * @date 2020/09/14
 */
@Controller
@RequestMapping("/base/processDocuments")
public class ProcessDocumentsController {

    @Autowired
    private ProcessDocumentsService processDocumentsService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("base:processDocuments:index")
    public String index(Model model, ProcessDocuments processDocuments) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("pcb_code", match -> match.contains());

        // 获取数据列表
        Example<ProcessDocuments> example = Example.of(processDocuments, matcher);
        Page<ProcessDocuments> list = processDocumentsService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/base/processDocuments/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("base:processDocuments:add")
    public String toAdd() {
        return "/base/processDocuments/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("base:processDocuments:edit")
    public String toEdit(@PathVariable("id") ProcessDocuments processDocuments, Model model) {
        model.addAttribute("processDocuments", processDocuments);
        return "/base/processDocuments/add";
    }

    /**
     * 保存添加/修改的数据

     */
    @PostMapping("/save")
    @RequiresPermissions({"base:processDocuments:add", "base:processDocuments:edit"})
    @ResponseBody
    public ResultVo save(@RequestParam("file") MultipartFile file, ProcessDocuments processDocuments,  HttpServletRequest request) {
        // 复制保留无需修改的数据
        if (processDocuments.getId() != null) {
            ProcessDocuments beProcessDocuments = processDocumentsService.getById(processDocuments.getId());
            EntityBeanUtil.copyProperties(beProcessDocuments, processDocuments);
        }
        String  nfile = null;
        String path = CommonConstant.file_path+CommonConstant.process_document_path;
        try {
            nfile = FileUtil.multipartFileToFileTimeStamp(file, path);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultVoUtil.error(20001, "保存文件错误，请联系系统管理员");
        }
        //FileUtil.upload(file);
        processDocuments.setUploadFile(nfile);
        processDocuments.setUploadTime(new Date());
        // 保存数据
        processDocumentsService.save(processDocuments);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 保存添加/修改的数据
     * @param
     */
    @PostMapping("/save2")
    @RequiresPermissions({"base:esop:add", "base:esop:edit"})
    @ResponseBody
    public ResultVo save2(ProcessDocuments processDocuments, HttpServletRequest request) {
        // 复制保留无需修改的数据
        if (processDocuments.getId() != null) {
            ProcessDocuments beProcessDocuments = processDocumentsService.findByPcbCode(processDocuments.getPcb_code());

            EntityBeanUtil.copyProperties(beProcessDocuments, processDocuments);

        }

        // 保存数据
        processDocumentsService.save(processDocuments);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("base:processDocuments:detail")
    public String toDetail(@PathVariable("id") ProcessDocuments processDocuments, Model model) {
        model.addAttribute("processDocuments",processDocuments);
        return "/base/processDocuments/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("base:processDocuments:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (processDocumentsService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }

    /**
     * 在线浏览PDF文件
     * @return
     */
    @RequestMapping("/showPDF")
    @ResponseBody
    public void showPDF(HttpServletResponse response, Long  id)throws IOException, DocumentException {
        processDocumentsService.showPDF(response,id);
    }


    /**
     * 根据规格型号在线浏览PDF文件
     * @return
     */
    @RequestMapping("/showPDFByPcbCode")
    @ResponseBody
    public void showPDFByPcbCode(HttpServletResponse response, String pcbCode)throws IOException, DocumentException {
        processDocumentsService.showPDFByPcbCode(response,pcbCode);
    }
}