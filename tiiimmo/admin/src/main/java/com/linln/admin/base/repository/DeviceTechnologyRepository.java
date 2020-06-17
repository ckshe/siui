package com.linln.admin.base.repository;

import com.linln.admin.base.domain.DeviceTechnology;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author www
 * @date 2020/05/14
 */
public interface DeviceTechnologyRepository extends BaseRepository<DeviceTechnology, Long> {
    @Query(value = "select distinct  device_code from base_device_technology",nativeQuery = true)
    List<String> queryDeviceTechnologyCode();
}