package com.linln.admin.base.repository;

import com.linln.admin.base.domain.Device;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author www
 * @date 2020/05/14
 */
public interface DeviceRepository extends BaseRepository<Device, Long> {

    @Query(value = "select  * from base_device where belong_process = ?1",nativeQuery = true)
    List<Device> findAllByBelong_process(String processName);


    @Query(value = "select * from base_device where device_code = ?1",nativeQuery = true)
    Device findbyDeviceCode(String deviceCode);


}