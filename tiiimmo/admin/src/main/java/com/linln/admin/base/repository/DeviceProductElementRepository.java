package com.linln.admin.base.repository;

import com.linln.admin.base.domain.DeviceProductElement;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author ww
 * @date 2020/06/17
 */
public interface DeviceProductElementRepository extends BaseRepository<DeviceProductElement, Long> {

    //根据机台编号查找所需程序
    @Query(value = "SELECT * FROM base_device_product_element WHERE device_code = ?1 and pcb_code = ?2 and a_or_b = ?3",nativeQuery = true)
    List<DeviceProductElement> findByDevice_code(String deviceCode,String pcbCode,String a_or_b);
}