package com.linln.admin.base.controller;

import com.linln.admin.base.domain.ESOP;
import com.linln.admin.base.domain.UserSignature;
import com.linln.admin.base.service.UserSignatureService;
import com.linln.admin.base.validator.UserSignatureValid;
import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.EntityBeanUtil;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.utils.StatusUtil;
import com.linln.common.vo.ResultVo;
import com.linln.component.shiro.ShiroUtil;
import com.linln.constant.CommonConstant;
import com.linln.utill.FileUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author www
 * @date 2020/09/14
 */
@Controller
@RequestMapping("/base/userSignature")
public class UserSignatureController {

    @Autowired
    private UserSignatureService userSignatureService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("base:userSignature:index")
    public String index(Model model, UserSignature userSignature) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("cardSequence", match -> match.contains());

        // 获取数据列表
        Example<UserSignature> example = Example.of(userSignature, matcher);
        Page<UserSignature> list = userSignatureService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/base/userSignature/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("base:userSignature:add")
    public String toAdd() {
        return "/base/userSignature/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("base:userSignature:edit")
    public String toEdit(@PathVariable("id") UserSignature userSignature, Model model) {
        model.addAttribute("userSignature", userSignature);
        return "/base/userSignature/add";
    }

    /**
     * 保存添加/修改的数据

     */
    @PostMapping("/save")
    @RequiresPermissions({"base:userSignature:add", "base:userSignature:edit"})
    @ResponseBody
    public ResultVo save(@RequestParam("picture") MultipartFile file, UserSignature userSignature, HttpServletRequest request) {



        UserSignature beUserSignature = userSignatureService.findByCardSequenceAndType(userSignature.getCardSequence(),userSignature.getType());

        if(beUserSignature!=null){
            userSignature.setId(beUserSignature.getId());
            userSignature.setCreateDate(new Date());
        }

        String  nfile = null;
        String path = CommonConstant.file_path+CommonConstant.sinature_path;
        try {
            nfile = FileUtil.multipartFileToFileTimeStamp(file, path);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultVoUtil.error(20001, "保存文件错误，请联系系统管理员");
        }
        //FileUtil.upload(file);
        userSignature.setPic_path(nfile);
        // 保存数据
        userSignatureService.save(userSignature);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 保存添加/修改的数据
     * @param
     */
    @PostMapping("/save2")
    @RequiresPermissions({"base:esop:add", "base:esop:edit"})
    @ResponseBody
    public ResultVo save2( UserSignature userSignature, HttpServletRequest request) {
        // 复制保留无需修改的数据
        if (userSignature.getId() != null) {
            UserSignature beUserSignature = userSignatureService.findByCardSequenceAndType(userSignature.getCardSequence(),userSignature.getType());

            EntityBeanUtil.copyProperties(beUserSignature, userSignature);

        }

        // 保存数据
        userSignatureService.save(userSignature);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("base:userSignature:detail")
    public String toDetail(@PathVariable("id") UserSignature userSignature, Model model) {
        model.addAttribute("userSignature",userSignature);
        return "/base/userSignature/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("base:userSignature:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (userSignatureService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }


    @PostMapping("/findUserSignature")
    @ResponseBody
    public ResultVo findUserSignature(@RequestBody UserSignature userSignature){
        UserSignature result = userSignatureService.findByCardSequenceAndType(userSignature.getCardSequence(),userSignature.getType());
        if(result==null){
            return ResultVoUtil.error("查无此人该类型签名");
        }else {
            String path = "/document/"+result.getPic_path();
            return ResultVoUtil.success("查询成功",path);
        }
    }
}