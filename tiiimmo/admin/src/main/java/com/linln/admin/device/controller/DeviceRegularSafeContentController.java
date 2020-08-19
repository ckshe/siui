package com.linln.admin.device.controller;

import com.linln.admin.device.entity.DeviceRegularSafeContent;
import com.linln.admin.device.enums.ResultEnum;
import com.linln.admin.device.exception.DeviceException;
import com.linln.admin.device.form.DeviceRegularSafeContentForm;
import com.linln.admin.device.form.DeviceRegularSafeContentListForm;
import com.linln.admin.device.realization.DeviceRegularSafeContentReal;
import com.linln.admin.device.resultVO.DeviceRegularSafeContentListResultVO;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@CrossOrigin()
@RestController
@RequestMapping("/deviceRegularSafeContent")
@Slf4j
public class DeviceRegularSafeContentController {
    private DeviceRegularSafeContentReal deviceRegularSafeContentReal;

    @Autowired
    public void setDeviceRegularSafeContentReal(DeviceRegularSafeContentReal deviceRegularSafeContentReal) {
        this.deviceRegularSafeContentReal = deviceRegularSafeContentReal;
    }

    @RequestMapping("/create")
    public ResultVO<Object> createDeviceRegularSafeContent(@Valid @RequestBody DeviceRegularSafeContentForm deviceRegularSafeContentForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【创建定期检查内容】参数不正确，deviceRegularSafeContentForm={}", deviceRegularSafeContentForm.toString());
            throw new DeviceException(ResultEnum.PARAM_ERROR.getCode(), Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        deviceRegularSafeContentForm.setRegularSafeContentId(null);
        deviceRegularSafeContentReal.createDeviceRegularSafeContent(deviceRegularSafeContentForm);
        return ResultVOUtil.success(null);
    }

    @RequestMapping("/list")
    public ResultVO<Object> getDeviceRegularSafeContents(@Valid @RequestBody DeviceRegularSafeContentListForm deviceRegularSafeContentListForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【获取定期检查列表】参数不正确，deviceRegularSafeContentListForm={}", deviceRegularSafeContentListForm.toString());
            throw new DeviceException(ResultEnum.PARAM_ERROR.getCode(), Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        DeviceRegularSafeContent deviceRegularSafeContent = new DeviceRegularSafeContent();
        BeanUtils.copyProperties(deviceRegularSafeContentListForm, deviceRegularSafeContent);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("deviceCode", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("safeContent", ExampleMatcher.GenericPropertyMatcher::contains);
        Example<DeviceRegularSafeContent> deviceRegularSafeContentExample = Example.of(deviceRegularSafeContent, matcher);
        Pageable pageable = PageRequest.of(deviceRegularSafeContentListForm.getPage() - 1, deviceRegularSafeContentListForm.getSize());
        DeviceRegularSafeContentListResultVO deviceRegularSafeContentListResultVO = deviceRegularSafeContentReal.getDeviceRegularSafeContents(deviceRegularSafeContentExample, pageable);
        return ResultVOUtil.success(deviceRegularSafeContentListResultVO);
    }

    @RequestMapping("/edit")
    public ResultVO<Object> editDeviceRegularSafeContent(@Valid @RequestBody DeviceRegularSafeContentForm deviceRegularSafeContentForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【编辑定期检查内容】参数不正确，deviceRegularSafeContentForm={}", deviceRegularSafeContentForm.toString());
            throw new DeviceException(ResultEnum.PARAM_ERROR.getCode(), Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        if (deviceRegularSafeContentForm.getRegularSafeContentId() == null) {
            log.error("【编辑定期检查内容】定期检测内容id不能为空，deviceRegularSafeContentForm={}", deviceRegularSafeContentForm.toString());
            throw new DeviceException(ResultEnum.DEVICE_REGULAR_SAFE_CONTENT_ID_NOT_NULL);
        }
        deviceRegularSafeContentReal.editDeviceRegularSafeContent(deviceRegularSafeContentForm);
        return ResultVOUtil.success(null);
    }

    @PostMapping("/delete")
    public ResultVO<Object> deleteDeviceSafe(@RequestParam("regularSafeId") Integer regularSafeId) {
        if (regularSafeId == null) {
            log.error("【删除定期检查内容】定期检查内容id不能为空，id={}", regularSafeId);
            throw new DeviceException(ResultEnum.DEVICE_REGULAR_SAFE_ID_NOT_NULL);
        }
        deviceRegularSafeContentReal.deleteDeviceRegularSafeContent(regularSafeId);
        return ResultVOUtil.success(null);
    }
}
