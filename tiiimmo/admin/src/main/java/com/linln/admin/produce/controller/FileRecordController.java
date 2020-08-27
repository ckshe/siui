package com.linln.admin.produce.controller;

import com.linln.admin.produce.domain.FileRecord;
import com.linln.admin.produce.service.FileRecordService;
import com.linln.admin.produce.validator.FileRecordValid;
import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.EntityBeanUtil;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.utils.StatusUtil;
import com.linln.common.vo.ResultVo;
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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author www
 * @date 2020/08/25
 */
@Controller
@RequestMapping("/produce/fileRecord")
public class FileRecordController {

    @Autowired
    private FileRecordService fileRecordService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("produce:fileRecord:index")
    public String index(Model model, FileRecord fileRecord) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("title", match -> match.contains())
                .withMatcher("pcb_code", match -> match.contains());

        // 获取数据列表
        Example<FileRecord> example = Example.of(fileRecord, matcher);
        Page<FileRecord> list = fileRecordService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/produce/fileRecord/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("produce:fileRecord:add")
    public String toAdd() {
        return "/produce/fileRecord/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("produce:fileRecord:edit")
    public String toEdit(@PathVariable("id") FileRecord fileRecord, Model model) {
        model.addAttribute("fileRecord", fileRecord);
        return "/produce/fileRecord/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"produce:fileRecord:add", "produce:fileRecord:edit"})
    @ResponseBody
    public ResultVo save(@Validated FileRecordValid valid, FileRecord fileRecord) {
        // 复制保留无需修改的数据
        if (fileRecord.getId() != null) {
            FileRecord beFileRecord = fileRecordService.getById(fileRecord.getId());
            EntityBeanUtil.copyProperties(beFileRecord, fileRecord);
        }

        // 保存数据
        fileRecordService.save(fileRecord);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("produce:fileRecord:detail")
    public String toDetail(@PathVariable("id") FileRecord fileRecord, Model model) {
        model.addAttribute("fileRecord",fileRecord);
        return "/produce/fileRecord/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("produce:fileRecord:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (fileRecordService.updateStatus(statusEnum, ids)) {
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
        fileRecordService.showPDF(response,id);

    }
}