package com.linln.admin.device.controller;

import com.linln.admin.device.VO.FirstQualityVO;
import com.linln.admin.device.entity.DeviceSafe;
import com.linln.admin.device.entity.FirstQuality;
import com.linln.admin.device.enums.ResultEnum;
import com.linln.admin.device.exception.DeviceException;
import com.linln.admin.device.form.DeviceSafeForm;
import com.linln.admin.device.form.DeviceSafeListForm;
import com.linln.admin.device.form.FirstQualityForm;
import com.linln.admin.device.form.FirstQualityListForm;
import com.linln.admin.device.formModel.FirstQualityEditFormModel;
import com.linln.admin.device.realization.FirstQualityReal;
import com.linln.admin.device.resultVO.DeviceSafeResultVO;
import com.linln.admin.device.resultVO.FirstQualityListResultVO;
import com.linln.admin.device.resultVO.ResultVO;
import com.linln.admin.device.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@CrossOrigin()
@RestController
@RequestMapping("/firstQuality")
@Slf4j
public class FirstQualityController {
    private FirstQualityReal firstQualityReal;

    @Autowired
    public void setFirstQualityReal(FirstQualityReal firstQualityReal) {
        this.firstQualityReal = firstQualityReal;
    }

    @RequestMapping("/get")
    public ResultVO<Object> getFirstQuality(@Valid @RequestBody FirstQualityForm firstQualityForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【获取工单首检记录】参数不正确，firstQualityForm={}", firstQualityForm.toString());
            throw new DeviceException(ResultEnum.PARAM_ERROR.getCode(), Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        List<FirstQualityVO> firstQualityVOS =  firstQualityReal.getFirstQuality(firstQualityForm);
        return ResultVOUtil.success(firstQualityVOS);
    }

    @RequestMapping("/edit")
    public ResultVO<Object> editFirstQuality(@Valid @RequestBody FirstQualityEditFormModel firstQualityEditFormModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【编辑工单首检记录】参数不正确，deviceSafeForm={}", firstQualityEditFormModel.toString());
            throw new DeviceException(ResultEnum.PARAM_ERROR.getCode(), Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        firstQualityReal.editFirstQuality(firstQualityEditFormModel);
        return ResultVOUtil.success(null);
    }

    @RequestMapping("/list")
    public ResultVO<Object> getFirstQualityList(@Valid @RequestBody FirstQualityListForm firstQualityListForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【获取工单首检记录列表】参数不正确，firstQualityListForm={}", firstQualityListForm.toString());
            throw new DeviceException(ResultEnum.PARAM_ERROR.getCode(), Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        FirstQuality firstQuality = new FirstQuality();
        BeanUtils.copyProperties(firstQualityListForm, firstQuality);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("pcbTaskCode", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("taskSheetCode", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("pcbCode", ExampleMatcher.GenericPropertyMatcher::contains);
        Example<FirstQuality> firstQualityExample = Example.of(firstQuality, matcher);
        Pageable pageable = PageRequest.of(firstQualityListForm.getPage()-1, firstQualityListForm.getSize());
        FirstQualityListResultVO firstQualityListResultVO = firstQualityReal.getFirstQualityList(firstQualityExample, pageable);
        return ResultVOUtil.success(firstQualityListResultVO);
    }
}
