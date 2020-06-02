package com.linln.admin.produce.repository;

import com.linln.admin.produce.domain.FeedingTask;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedingTaskRepository extends BaseRepository<FeedingTask,Long> {

    @Query(value = "select * from produce_feeding_task where  feeding_task_code =?1",nativeQuery = true)
    List<FeedingTask> findByFeeding_task_code(String feedingTaskCode);
}
