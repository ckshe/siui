package com.linln.admin.device.controller;

import com.linln.admin.device.VO.DeviceDateSafeVO;
import com.linln.admin.device.entity.DeviceDateSafe;
import com.linln.admin.device.enums.ResultEnum;
import com.linln.admin.device.exception.DeviceException;
import com.linln.admin.device.form.DeviceDateSafeForm;
import com.linln.admin.device.form.DeviceDateSafeListForm;
import com.linln.admin.device.form.DeviceSafeForm;
import com.linln.admin.device.formModel.DeviceDateSafeEditFormModel;
import com.linln.admin.device.realization.DeviceDateSafeReal;
import com.linln.admin.device.resultVO.DeviceDateSafeListResultVO;
import com.linln.admin.device.resultVO.DeviceDateSafeResultVO;
import com.linln.admin.device.resultVO.ResultVO;
import com.linln.admin.device.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@CrossOrigin()
@RestController
@RequestMapping("/deviceDateSafe")
@Slf4j
public class DeviceDateSafeController {
    private DeviceDateSafeReal deviceDateSafeReal;

    @Autowired
    public void setDeviceDateSafeReal(DeviceDateSafeReal deviceDateSafeReal) {
        this.deviceDateSafeReal = deviceDateSafeReal;
    }

    @RequestMapping("/list")
    public ResultVO<Object> getDeviceDateSafes(@Valid @RequestBody DeviceDateSafeListForm deviceDateSafeListForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【查询设备日常维护内容】参数不正确，deviceDateSafeListForm={}", deviceDateSafeListForm.toString());
            throw new DeviceException(ResultEnum.PARAM_ERROR.getCode(), Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        Pageable pageable = PageRequest.of(deviceDateSafeListForm.getPage() - 1, deviceDateSafeListForm.getSize());
        Specification<DeviceDateSafe> Specification = (Specification<DeviceDateSafe>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (deviceDateSafeListForm.getSafeDeviceCode() != null) {
                predicate.getExpressions().add(criteriaBuilder.like(root.get("safeDeviceCode"), "%" + deviceDateSafeListForm.getSafeDeviceCode() + "%"));
            }
            if (deviceDateSafeListForm.getSafeTimeStart() != null) {
                predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("safeTime"), deviceDateSafeListForm.getSafeTimeStart()));
            }
            if (deviceDateSafeListForm.getSafeTimeEnd()!=null){
                predicate.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(root.get("safeTime"), deviceDateSafeListForm.getSafeTimeEnd()));
            }
            return predicate;
        };
        DeviceDateSafeListResultVO deviceDateSafeListResultVO = deviceDateSafeReal.getDeviceDateSafes(Specification, pageable);
        return ResultVOUtil.success(deviceDateSafeListResultVO);
    }

    @RequestMapping("/get")
    public ResultVO<Object> getDeviceDateSafesByDate(@Valid @RequestBody DeviceDateSafeForm deviceDateSafeForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【获取设备日常维护内容】参数不正确，deviceDateSafeForm={}", deviceDateSafeForm.toString());
            throw new DeviceException(ResultEnum.PARAM_ERROR.getCode(), Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        DeviceDateSafeResultVO deviceDateSafeResultVO = deviceDateSafeReal.getDeviceDateSafesByDate(deviceDateSafeForm);
        return ResultVOUtil.success(deviceDateSafeResultVO);
    }

    @RequestMapping("/edit")
    public ResultVO<Object> editDeviceDateSafes(@Valid @RequestBody DeviceDateSafeEditFormModel deviceDateSafeEditFormModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【编辑设备日常维护内容】参数不正确，deviceDateSafeEditFormModel={}", deviceDateSafeEditFormModel.toString());
            throw new DeviceException(ResultEnum.PARAM_ERROR.getCode(), Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        deviceDateSafeReal.editDeviceDateSafes(deviceDateSafeEditFormModel);
        return ResultVOUtil.success(null);
    }
}
