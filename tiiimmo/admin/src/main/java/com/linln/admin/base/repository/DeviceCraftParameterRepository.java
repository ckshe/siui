package com.linln.admin.base.repository;

import com.linln.admin.base.domain.DeviceCraftParameter;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author www
 * @date 2020/08/18
 */
public interface DeviceCraftParameterRepository extends BaseRepository<DeviceCraftParameter, Long> {

    @Query(value = "select  * from base_device_craft_parameter where device_code = ?1",nativeQuery = true)
    List<DeviceCraftParameter> findByDevice_code(String deviceCode);
}