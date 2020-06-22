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
    @Query(value = "select  * from base_device_technology where id = ?1",nativeQuery = true)
    DeviceTechnology findByDId(Long id);
    @Query(value = "select  top 1 * from base_device_technology where sort_no > ?1 order by sort_no asc ",nativeQuery = true)
    DeviceTechnology moveDown(Integer sort_no);
    @Query(value = "select  top 1 * from base_device_technology where sort_no < ?1 order by sort_no desc ",nativeQuery = true)
    DeviceTechnology moveUp(Integer sort_no);
}