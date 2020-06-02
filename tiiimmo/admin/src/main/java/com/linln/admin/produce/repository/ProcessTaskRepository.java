package com.linln.admin.produce.repository;

import com.linln.admin.produce.domain.ProcessTask;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProcessTaskRepository extends BaseRepository<ProcessTask,Long> {

    @Query(value = "select * from produce_process_task where pcb_task_id = ?1",nativeQuery = true)
    List<ProcessTask> findAllByPcb_task_id(Long id);





}
