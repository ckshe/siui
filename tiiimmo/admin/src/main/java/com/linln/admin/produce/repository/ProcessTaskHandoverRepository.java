package com.linln.admin.produce.repository;

import com.linln.admin.produce.domain.ProcessTaskHandover;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProcessTaskHandoverRepository extends BaseRepository<ProcessTaskHandover,Long> {


    @Query(value = "select * from produce_process_task_handover where process_task_code = ?1",nativeQuery = true)
    List<ProcessTaskHandover> findPRocessTaskHandover(String processTaskCode);
}
