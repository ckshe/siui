package com.linln.admin.device.serviceInter;

import com.linln.admin.device.entity.FirstQuality;

import java.util.List;

public interface FirstQualityServiceInter {
    List<FirstQuality> findByPcbTaskCode(String pcbTaskCode);
    void saveAllFirstQuality(List<FirstQuality> firstQualities);
}
