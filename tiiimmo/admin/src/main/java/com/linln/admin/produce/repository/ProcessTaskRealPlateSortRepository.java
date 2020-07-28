package com.linln.admin.produce.repository;

import com.linln.admin.produce.domain.ProcessTaskRealPlateSort;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProcessTaskRealPlateSortRepository extends BaseRepository<ProcessTaskRealPlateSort,Long> {

    @Query(value = "select * from produce_process_task_real_platesort where process_task_code = ?1  and plate_no =?2",nativeQuery = true)
    ProcessTaskRealPlateSort findByProcess_task_codeAndPlate_no(String processTaskCode,String plateNo);

    @Query(value = "select  * from produce_process_task_real_platesort where process_task_code = ?1 order by record_start_time",nativeQuery = true)
    List<ProcessTaskRealPlateSort> findByProcess_task_code(String processTaskCode);
}
