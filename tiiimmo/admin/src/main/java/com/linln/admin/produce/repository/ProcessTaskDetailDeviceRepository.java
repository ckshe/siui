package com.linln.admin.produce.repository;

import com.linln.admin.produce.domain.ProcessTaskDetailDevice;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ProcessTaskDetailDeviceRepository extends BaseRepository<ProcessTaskDetailDevice,Long> {

    @Query(value = "select * from produce_process_task_detail_device where process_task_code = ?1 and  CONVERT ( VARCHAR ( 100 ), plan_day_time, 23 ) = ?2",nativeQuery = true)
    List<ProcessTaskDetailDevice> findByTaskCodeAndDayTime(String processTaskCode,String planDayTime);

    @Query(value = "select * from produce_process_task_detail_device where process_task_code = ?1 and  CONVERT ( VARCHAR ( 100 ), plan_day_time, 23 ) = ?2 and device_code = ?3",nativeQuery = true)
    List<ProcessTaskDetailDevice> findByTaskCodeAndDayTimeAndDeviceCode(String processTaskCode,String planDayTime,String deviceCode);


    @Query(value = "select * from produce_process_task_detail_device where process_task_code = ?1 and  CONVERT ( VARCHAR ( 100 ), plan_day_time, 23 ) = ?2 and device_code = ?3",nativeQuery = true)
    ProcessTaskDetailDevice findByTaskCodeAndDayTimeAndDeviceCode1(String processTaskCode,String planDayTime,String deviceCode);

    @Modifying
    @Transactional
    @Query(value = "delete from produce_process_task_detail_device where process_task_code = ?1 ",nativeQuery = true)
    void deleteByTaskCode(String processTaskCode);

}
