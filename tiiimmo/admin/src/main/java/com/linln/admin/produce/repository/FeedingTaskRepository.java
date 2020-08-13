package com.linln.admin.produce.repository;

import com.linln.admin.produce.domain.FeedingTask;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface FeedingTaskRepository extends BaseRepository<FeedingTask,Long> {

    @Query(value = "select * from produce_feeding_task where  feeding_task_code =?1 ORDER BY fqtlv asc",nativeQuery = true)
    List<FeedingTask> findByFeeding_task_code(String feedingTaskCode);


    @Modifying
    @Transactional
    @Query(value = "delete from produce_feeding_task where  feeding_task_code =?1",nativeQuery = true)
    void deleteByFeeding_task_code(String feedingTaskCode);


}
