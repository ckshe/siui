package com.linln.admin.produce.repository;

import com.linln.admin.produce.domain.ProcessTaskDeviceTheoryTime;
import com.linln.modules.system.repository.BaseRepository;
import net.sf.ehcache.search.impl.BaseResult;
import org.springframework.data.jpa.repository.Query;

public interface ProcessTaskDeviceTheoryTimeRepository extends BaseRepository<ProcessTaskDeviceTheoryTime,Long> {

    @Query(value = "select * from produce_process_task_device_theorytime where process_task_code = ?1 and  device_code = ?2",nativeQuery = true)
    ProcessTaskDeviceTheoryTime findByProcess_task_codeAndDevice_code(String processTaskCode,String deviceCode);

}
