package com.linln.admin.device.realization;

import com.linln.admin.device.VO.FirstQualityVO;
import com.linln.admin.device.entity.FirstQuality;
import com.linln.admin.device.entity.QualityType;
import com.linln.admin.device.form.FirstQualityForm;
import com.linln.admin.device.serviceImpl.FirstQualityServiceImpl;
import com.linln.admin.device.serviceImpl.QualityTypeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
}
