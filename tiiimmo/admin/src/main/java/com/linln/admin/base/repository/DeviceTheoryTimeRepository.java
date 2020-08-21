package com.linln.admin.base.repository;

import com.linln.admin.base.domain.DeviceTheoryTime;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author ww
 * @date 2020/07/30
 */
public interface DeviceTheoryTimeRepository extends BaseRepository<DeviceTheoryTime, Long> {

    @Query(value = "select  * from base_device_theory_time where device_code =?1 and pcb_code = ?2",nativeQuery = true)
    DeviceTheoryTime findByDevice_codeAndPcb_code(String device,String pcbCode);

    @Query(value = "select  * from base_device_theory_time where device_code =?1 and pcb_code = ?2 and a_or_b = ?3",nativeQuery = true)
    DeviceTheoryTime findByDevice_codeAndPcb_codeAndAB(String device,String pcbCode,String aOrB);
}