package com.linln.admin.produce.repository;

import com.linln.admin.produce.domain.ProcessTaskDevice;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ProcessTaskDeviceRepository extends BaseRepository<ProcessTaskDevice,Long> {


    @Query(value = "select * from produce_process_task_device where device_code = ?1 and process_task_code=?2 ",nativeQuery = true)
    ProcessTaskDevice findByPTCodeDeviceCode(String deviceCode,String processTaskCode);


    @Query(value = "SELECT t1.* FROM produce_process_task_device t1 LEFT JOIN base_device t2 on t1.device_code = t2.device_code WHERE t1.process_task_code = ?1 ORDER BY t2.device_sort  ",nativeQuery = true)
    List<ProcessTaskDevice> findByPTCode(String processTaskCode);


}
