package com.linln.admin.device.controller;

import com.linln.admin.device.entity.DeviceDateSafe;
import com.linln.admin.device.enums.ResultEnum;
import com.linln.admin.device.exception.DeviceException;
import com.linln.admin.device.form.DeviceAmbientForm;
import com.linln.admin.device.form.DeviceAmbientListForm;
import com.linln.admin.device.form.DeviceDateSafeListForm;
import com.linln.admin.device.form.DeviceRegularSafeContentForm;
import com.linln.admin.device.realization.DeviceAmbientReal;
import com.linln.admin.device.resultVO.DeviceDateSafeListResultVO;
import com.linln.admin.device.resultVO.ResultVO;
import com.linln.admin.device.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Predicate;
import javax.validation.Valid;
import java.util.Objects;

@CrossOrigin()
@RestController
@RequestMapping("/deviceAmbient")
@Slf4j
public class DeviceAmbientController {
    private DeviceAmbientReal deviceAmbientReal;

    @Autowired
    public void setDeviceAmbientReal(DeviceAmbientReal deviceAmbientReal) {
        this.deviceAmbientReal = deviceAmbientReal;
    }

    @RequestMapping("/create")
    public ResultVO<Object> createDeviceAmbient(@Valid @RequestBody DeviceAmbientForm deviceAmbientForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【创建温湿度记录】参数不正确，deviceAmbientForm={}", deviceAmbientForm.toString());
            throw new DeviceException(ResultEnum.PARAM_ERROR.getCode(), Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        deviceAmbientForm.setAmbientId(null);
        deviceAmbientReal.createDeviceAmbient(deviceAmbientForm);
        return ResultVOUtil.success(null);
    }

    @RequestMapping("/edit")
    public ResultVO<Object> editDeviceAmbient(@Valid @RequestBody DeviceAmbientForm deviceAmbientForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【编辑温湿度记录】参数不正确，deviceAmbientForm={}", deviceAmbientForm.toString());
            throw new DeviceException(ResultEnum.PARAM_ERROR.getCode(), Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        if (deviceAmbientForm.getAmbientId() == null) {
            log.error("【编辑温湿度记录】温湿度记录id不能为空，deviceAmbientForm={}", deviceAmbientForm.toString());
            throw new DeviceException(ResultEnum.DEVICE_REGULAR_SAFE_CONTENT_ID_NOT_NULL);
        }
        deviceAmbientReal.editDeviceAmbient(deviceAmbientForm);
        return ResultVOUtil.success(null);
    }

    @PostMapping("/delete")
    public ResultVO<Object> deleteDeviceAmbient(@RequestParam("ambientId") Integer ambientId) {
        if (ambientId == null) {
            log.error("【删除温湿度记录】 ，id={}", ambientId);
            throw new DeviceException(ResultEnum.DEVICE_AMBIENT_ID_NOT_NULL);
        }
        deviceAmbientReal.deleteDeviceAmbient(ambientId);
        return ResultVOUtil.success(null);
    }

    @RequestMapping("/list")
    public ResultVO<Object> getDeviceAmbientList(@Valid @RequestBody DeviceAmbientListForm deviceAmbientListForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【查询设备日常维护内容】参数不正确，deviceAmbientListForm={}", deviceAmbientListForm.toString());
            throw new DeviceException(ResultEnum.PARAM_ERROR.getCode(), Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        Pageable pageable = PageRequest.of(deviceAmbientListForm.getPage() - 1, deviceAmbientListForm.getSize());
        Specification<DeviceDateSafe> Specification = (Specification<DeviceDateSafe>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (deviceAmbientListForm.getAmbientTimeStart() != null) {
                predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("ambientRecordTime"), deviceAmbientListForm.getAmbientTimeStart()));
            }
            if (deviceAmbientListForm.getAmbientTimeEnd()!=null){
                predicate.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(root.get("ambientRecordTime"), deviceAmbientListForm.getAmbientTimeEnd()));
            }
            return predicate;
        };

        return ResultVOUtil.success(null);
    }
}
