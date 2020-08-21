package com.linln.admin.device.serviceInter;

import com.linln.admin.device.entity.DeviceSafe;
import com.linln.admin.device.entity.FirstQuality;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FirstQualityServiceInter {
    List<FirstQuality> findByPcbTaskCode(String pcbTaskCode);
    void saveAllFirstQuality(List<FirstQuality> firstQualities);
    List<FirstQuality> findByQualityIdIn(List<Integer> idList);
    Page<FirstQuality> getFirstQualityList(Example<FirstQuality> firstQualityExample, Pageable pageable);
}
