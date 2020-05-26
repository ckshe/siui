package com.linln.admin.produce.repository;


import com.linln.admin.produce.domain.ScheduleJobApi;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

public interface ScheduleJobApiRepository extends BaseRepository<ScheduleJobApi,Integer> {
    @Query(value = "select * from schedule_job where id in (?1)",nativeQuery = true)
    List<ScheduleJobApi> findAllByIdIn(String[] ids);

    ScheduleJobApi findAllByApiName(String name);

}
