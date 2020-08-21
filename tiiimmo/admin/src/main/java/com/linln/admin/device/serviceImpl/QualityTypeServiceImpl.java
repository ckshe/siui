package com.linln.admin.device.serviceImpl;

import com.linln.admin.device.entity.QualityType;
import com.linln.admin.device.repository.QualityTypeRepository;
import com.linln.admin.device.serviceInter.QualityTypeServiceInter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class QualityTypeServiceImpl implements QualityTypeServiceInter {
    private QualityTypeRepository qualityTypeRepository;

    @Autowired
    public void setQualityTypeRepository(QualityTypeRepository qualityTypeRepository){
        this.qualityTypeRepository = qualityTypeRepository;
    }

    @Override
    public List<QualityType> findAllQualityTypes() {
        return qualityTypeRepository.findAll();
    }
}
