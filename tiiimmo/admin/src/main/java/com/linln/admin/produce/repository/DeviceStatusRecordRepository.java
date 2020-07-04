package com.linln.admin.produce.repository;

import com.linln.admin.quality.domain.DeviceStatusRecord;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeviceStatusRecordRepository extends BaseRepository<DeviceStatusRecord,Long> {

    @Query(value = "SELECT * FROM base_device_status_record WHERE end_time is NULL AND device_code = ?1",nativeQuery = true)
    DeviceStatusRecord findByDevice_code(String deviceCode);

    @Query(value = "SELECT * FROM base_device_status_record WHERE  device_code = ?1 order  by id desc",nativeQuery = true)
    List<DeviceStatusRecord> findByDevice_codeLastOne(String deviceCode);
}
