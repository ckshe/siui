package com.linln.admin.produce.repository;

import com.linln.admin.produce.domain.ProcessTaskDetail;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ProcessTaskDetailRepositoty  extends BaseRepository<ProcessTaskDetail,Long> {


    @Query(value = "select * from produce_process_task_detail where process_task_code = ?1",nativeQuery = true)
    List<ProcessTaskDetail> findByProcess_task_code(String processTaskCode);

    @Modifying
    @Transactional
    @Query(value = "delete from produce_process_task_detail where process_task_code = ?1",nativeQuery = true)
    void deleteByByProcess_task_code(String processTaskCode);

    @Query(value = "select * from produce_process_task_detail where process_task_code = ?1 and  CONVERT ( VARCHAR ( 100 ), plan_day_time, 23 ) = ?2",nativeQuery = true)
    ProcessTaskDetail findAllByProcess_task_codeAndPlan_day_time(String processTaskCode,String planDay);

    @Query(value = "select * from produce_process_task_detail where process_task_code = ?1 and  CONVERT ( VARCHAR ( 100 ), plan_day_time, 23 ) < ?2",nativeQuery = true)
    List<ProcessTaskDetail> findAllByProcess_task_codeAndBefoPlan_day_time(String processTaskCode,String planDay);

    @Query(value = "select * from produce_process_task_detail where plan_day_time >= ?1 AND plan_day_time <= ?2 and detail_type = '人创建' and plan_count !=0 ",nativeQuery = true)
    List<ProcessTaskDetail> findBetweenTime(String startTime,String endTime);

}
