package com.linln.admin.produce.repository;

import com.linln.admin.produce.domain.ProcessTaskRealPlateSort;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProcessTaskRealPlateSortRepository extends BaseRepository<ProcessTaskRealPlateSort,Long> {

    @Query(value = "select * from produce_process_task_real_platesort where process_task_code = ?1 and device_code =?2 and plate_no =?3",nativeQuery = true)
    ProcessTaskRealPlateSort findByProcess_task_codeAndDevice_codeAndPlate_no(String processTaskCode,String deviceCode,String plateNo);
}
