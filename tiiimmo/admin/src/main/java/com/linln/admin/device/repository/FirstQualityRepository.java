package com.linln.admin.device.repository;

import com.linln.admin.device.entity.FirstQuality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FirstQualityRepository extends JpaRepository<FirstQuality, Integer> {
    List<FirstQuality> findByPcbTaskCode(String pcbTaskCode);
}
