package com.linln.admin.device.serviceImpl;

import com.linln.admin.device.entity.FirstQuality;
import com.linln.admin.device.repository.FirstQualityRepository;
import com.linln.admin.device.serviceInter.FirstQualityServiceInter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FirstQualityServiceImpl implements FirstQualityServiceInter {
    private FirstQualityRepository firstQualityRepository;

    @Autowired
    public void setFirstQualityRepository(FirstQualityRepository firstQualityRepository){
        this.firstQualityRepository = firstQualityRepository;
    }

    @Override
    public List<FirstQuality> findByPcbTaskCode(String pcbTaskCode) {
        return firstQualityRepository.findByPcbTaskCode(pcbTaskCode);
    }

    @Override
    public void saveAllFirstQuality(List<FirstQuality> firstQualities) {
        firstQualityRepository.saveAll(firstQualities);
    }
}
