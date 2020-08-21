package com.linln.admin.device.serviceImpl;

import com.linln.admin.device.entity.FirstQuality;
import com.linln.admin.device.repository.FirstQualityRepository;
import com.linln.admin.device.serviceInter.FirstQualityServiceInter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public List<FirstQuality> findByQualityIdIn(List<Integer> idList) {
        return firstQualityRepository.findByQualityIdIn(idList);
    }

    @Override
    public Page<FirstQuality> getFirstQualityList(Example<FirstQuality> firstQualityExample, Pageable pageable) {
        return firstQualityRepository.findAll(firstQualityExample, pageable);
    }
}
