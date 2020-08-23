package com.linln.admin.device.realization;

import com.linln.admin.device.VO.FirstQualityVO;
import com.linln.admin.device.entity.DeviceSafe;
import com.linln.admin.device.entity.FirstQuality;
import com.linln.admin.device.entity.QualityType;
import com.linln.admin.device.enums.ResultEnum;
import com.linln.admin.device.exception.DeviceException;
import com.linln.admin.device.form.FirstQualityEditForm;
import com.linln.admin.device.form.FirstQualityForm;
import com.linln.admin.device.formModel.FirstQualityEditFormModel;
import com.linln.admin.device.resultVO.FirstQualityListResultVO;
import com.linln.admin.device.serviceImpl.FirstQualityServiceImpl;
import com.linln.admin.device.serviceImpl.QualityTypeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FirstQualityReal {
    private FirstQualityServiceImpl firstQualityService;
    private QualityTypeServiceImpl qualityTypeService;

    @Autowired
    public void setFirstQualityService(FirstQualityServiceImpl firstQualityService){
        this.firstQualityService = firstQualityService;
    }

    @Autowired
    public void setQualityTypeService(QualityTypeServiceImpl qualityTypeService){
        this.qualityTypeService = qualityTypeService;
    }

    public List<FirstQualityVO> getFirstQuality(FirstQualityForm firstQualityForm){
        List<FirstQualityVO> firstQualityVOS = new ArrayList<>();
        List<FirstQuality> firstQualities = firstQualityService.findByPcbTaskCode(firstQualityForm.getPcbTaskCode());
        if (firstQualities.size() == 0){
            List<QualityType> qualityTypes = qualityTypeService.findAllQualityTypes();
            for (QualityType qualityType : qualityTypes){
                FirstQuality firstQuality = new FirstQuality();
                BeanUtils.copyProperties(firstQualityForm, firstQuality);
                firstQuality.setSegment(qualityType.getQualityTypeName());
                firstQualities.add(firstQuality);
            }
            firstQualityService.saveAllFirstQuality(firstQualities);
            firstQualities = firstQualityService.findByPcbTaskCode(firstQualityForm.getPcbTaskCode());
        }
        for (FirstQuality firstQuality : firstQualities){
            FirstQualityVO firstQualityVO = new FirstQualityVO();
            BeanUtils.copyProperties(firstQuality, firstQualityVO);
            firstQualityVOS.add(firstQualityVO);
        }
        return firstQualityVOS;
    }

    public void editFirstQuality(FirstQualityEditFormModel firstQualityEditFormModel){
        List<Integer> idList = firstQualityEditFormModel.getFirstQualityEditForms().stream().map(FirstQualityEditForm::getQualityId).collect(Collectors.toList());
        List<FirstQuality> firstQualities = firstQualityService.findByQualityIdIn(idList);
        int index=0;
        for (FirstQuality firstQuality : firstQualities){
            List<FirstQualityEditForm> firstQualityEditForms = firstQualityEditFormModel.getFirstQualityEditForms().stream().filter(e -> e.getQualityId().equals(firstQuality.getQualityId())).collect(Collectors.toList());
            if (firstQualityEditForms.size() != 0){
                FirstQualityEditForm firstQualityEditForm = firstQualityEditForms.get(0);
                BeanUtils.copyProperties(firstQualityEditForm, firstQualities.get(index));
                firstQualities.get(index).setPcbCode(firstQualityEditForm.getPcbCode());
                firstQualities.get(index).setPcbTaskCode(firstQualityEditForm.getPcbTaskCode());
                firstQualities.get(index).setTaskSheetCode(firstQualityEditForm.getTaskSheetCode());
                firstQualities.get(index).setSegment(firstQualityEditForm.getSegment());
                if (firstQualityEditForm.getQualityOne()!=null&&firstQualities.get(index).getQualityDate()==null){
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date();
                    String dateStr = format.format(date);
                    try {
                        date = format.parse(dateStr);
                    } catch (ParseException e) {
                        log.error("【编辑首检记录】日期转换异常 date={}", date);
                        e.printStackTrace();
                        throw new DeviceException(ResultEnum.DATE_FORMAT_ERROR);
                    }
                    firstQualities.get(index).setQualityDate(date);
                }
            }
            index++;
        }
        firstQualityService.saveAllFirstQuality(firstQualities);
    }

    public FirstQualityListResultVO getFirstQualityList(Example<FirstQuality> firstQualityExample, Pageable pageable){
        FirstQualityListResultVO firstQualityListResultVO = new FirstQualityListResultVO();
        List<FirstQualityVO> firstQualityVOS = new ArrayList<>();
        Page<FirstQuality> firstQualityPage = firstQualityService.getFirstQualityList(firstQualityExample, pageable);
        for (FirstQuality firstQuality : firstQualityPage.getContent()){
            FirstQualityVO firstQualityVO = new FirstQualityVO();
            BeanUtils.copyProperties(firstQuality, firstQualityVO);
            firstQualityVOS.add(firstQualityVO);
        }
        firstQualityListResultVO.setTotal(firstQualityPage.getTotalElements());
        firstQualityListResultVO.setFirstQualityVOS(firstQualityVOS);
        return firstQualityListResultVO;
    }
}
