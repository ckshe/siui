package com.linln.admin.produce.repository;

import com.linln.admin.produce.domain.ProcessTaskDevice;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ProcessTaskDeviceRepository extends BaseRepository<ProcessTaskDevice,Long> {


    @Query(value = "select * from produce_process_task_device where device_code = ?1 and process_task_code=?2 ",nativeQuery = true)
    ProcessTaskDevice findByPTCodeDeviceCode(String deviceCode,String processTaskCode);


    @Query(value = "select * from produce_process_task_device where  process_task_code=?1 ",nativeQuery = true)
    List<ProcessTaskDevice> findByPTCode(String processTaskCode);


}
