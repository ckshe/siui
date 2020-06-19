package com.linln.admin.base.repository;

import com.linln.admin.base.domain.DeviceCropRate;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author ww
 * @date 2020/06/19
 */
public interface DeviceCropRateRepository extends BaseRepository<DeviceCropRate, Long> {

    @Query(value = "SELECT * FROM base_device_crop_rate WHERE device_code = ?1 AND CONVERT( VARCHAR ( 100 ), record_time, 23 ) = ?2",nativeQuery = true)
    List<DeviceCropRate> findByDevice_codeAndRecord_time(String deviceCode,String today);
}