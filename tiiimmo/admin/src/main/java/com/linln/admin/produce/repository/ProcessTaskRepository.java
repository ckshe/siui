package com.linln.admin.produce.repository;

import com.linln.admin.produce.domain.ProcessTask;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProcessTaskRepository extends BaseRepository<ProcessTask,Long> {

    @Query(value = "select * from produce_process_task where pcb_task_id = ?1",nativeQuery = true)
    List<ProcessTask> findAllByPcb_task_id(Long id);

    @Query(value = "select * from produce_process_task where device_code like ?1 and process_task_status = '生产中'",nativeQuery = true)
    ProcessTask findProducingByDevice_code(String device);

    @Query(value = "select * from produce_process_task where device_code like ?1 and (process_task_status = '生产中' or process_task_status like '%已下达%' or process_task_status like '%暂停%')",nativeQuery = true)
    List<ProcessTask> findByDevice_code(String device);

    @Query(value = "SELECT * FROM produce_process_task where plan_finish_time >= ?1 AND plan_finish_time <= ?2",nativeQuery = true)
    List<ProcessTask> findByStartEndTime(String startTime,String endTime);

    @Query(value = "SELECT * FROM produce_process_task where process_task_code = ?1",nativeQuery = true)
    ProcessTask findByProcessTaskCode(String processTaskCode);

}
