package com.linln.admin.produce.repository;

import com.linln.admin.produce.domain.ProcessTaskStatusHistory;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProcessTaskStatusHistoryRepository extends BaseRepository<ProcessTaskStatusHistory,Long> {


    @Query(value = "select * from produce_process_task_status_history where process_task_code = ?1 AND start_time is NOT null and end_time is null",nativeQuery = true)
    ProcessTaskStatusHistory findByProcessTaskCodeLastRecord(String processTaskCode);

    @Query(value = "select * from produce_process_task_status_history where process_task_code = ?1 and (process_task_status = '进行中' or process_task_status = '生产中')",nativeQuery = true)
    List<ProcessTaskStatusHistory> findByProcessTaskCode(String processTaskCode);

}