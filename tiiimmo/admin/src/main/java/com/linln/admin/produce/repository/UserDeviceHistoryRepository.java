package com.linln.admin.produce.repository;

import com.linln.admin.produce.domain.UserDeviceHistory;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import sun.rmi.runtime.Log;

import java.util.List;

public interface UserDeviceHistoryRepository extends BaseRepository<UserDeviceHistory, Log> {

    @Query(value = "SELECT * FROM produce_user_device_history WHERE down_time is NULL AND up_time is not null AND device_code = ?1",nativeQuery = true)
    UserDeviceHistory findOnlyUpTimeRecord(String deviceCode);

    //根据日期员工设备工序计划查找上下机记录
    @Query(value = "SELECT * FROM produce_user_device_history WHERE process_task_code=?1 AND  CONVERT(varchar(100), up_time, 23) = ?2 AND device_code = ?3 and user_id = ?4  AND down_time = null",nativeQuery = true)
    UserDeviceHistory findAllByProcessTaskDateDeviceUser(String processTaskCode,String date,String deviceCode,Long userId);

    @Query(value = "SELECT * FROM produce_user_device_history WHERE process_task_code= ?1 and device_code =?1  AND do_type = '上机'",nativeQuery = true)
    UserDeviceHistory findAllByNoInputDevice(String deviceCode);

    @Query(value = "SELECT * FROM produce_user_device_history WHERE device_code = ?1 ORDER BY id DESC ",nativeQuery = true)
    List<UserDeviceHistory> findOneLastOnTime(String device);


    @Query(value = "SELECT * FROM produce_user_device_history WHERE device_code = ?1 AND up_time is not NULL AND down_time is NULL ",nativeQuery = true)
    UserDeviceHistory findOneLastOnTimeNotDown(String device);

    @Query(value = "SELECT * FROM produce_user_device_history WHERE down_time is NULL AND up_time is not null ",nativeQuery = true)
    List<UserDeviceHistory> findOnlyUpTimeRecordList();



}
