package com.linln.admin.base.repository;

import com.linln.admin.base.domain.ProcessTaskDeviceTheorytime;
import com.linln.admin.produce.domain.ProcessTaskDeviceTheoryTime;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author lian
 * @date 2020/08/20
 */
public interface ProcessTaskDeviceTheorytimeRepository extends BaseRepository<ProcessTaskDeviceTheorytime, Long> {
    @Query(value = "select * from produce_process_task_device_theorytime where process_task_code = ?1 and  device_code = ?2",nativeQuery = true)
    ProcessTaskDeviceTheoryTime findByProcess_task_codeAndDevice_code(String processTaskCode, String deviceCode);
}