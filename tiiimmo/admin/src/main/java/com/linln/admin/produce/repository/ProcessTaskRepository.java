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

    @Query(value = "SELECT * FROM produce_process_task where plan_finish_time >= ?1 AND plan_finish_time <= ?2 order by finish_time desc",nativeQuery = true)
    List<ProcessTask> findByStartEndTime(String startTime,String endTime);

    @Query(value = "SELECT * FROM produce_process_task where process_task_code = ?1",nativeQuery = true)
    ProcessTask findByProcessTaskCode(String processTaskCode);


    @Query(value = "SELECT\n" +
            "    t1.*\n" +
            "    FROM\n" +
            "    produce_process_task t1\n" +
            "    LEFT JOIN base_process t2 ON t2.name = t1.process_name\n" +
            "    WHERE  t1.plan_finish_time >= ?1 AND t1.plan_finish_time <= ?2 and t2.process_type = ?3 ",nativeQuery = true)
    List<ProcessTask> findByStartEndTimeProcessType(String startTime,String endTime,String processType);


    @Query(value = "SELECT\n" +
            "\t*\n" +
            "    FROM\n" +
            "            produce_process_task\n" +
            "    WHERE\n" +
            "            (device_code LIKE '%B15002%'\n" +
            "                    OR device_code LIKE '%B15003%'\n" +
            "                    OR device_code LIKE '%B1902001%') AND plan_finish_time >= ?1 AND plan_finish_time <=?2",nativeQuery = true)
    List<ProcessTask> findByStartEndTimeBy3TiePian(String startTime,String endTime);

    @Query(value = "select * from produce_process_task where id = ?1 ",nativeQuery = true)
    ProcessTask findByProcessId(Long id);
}
