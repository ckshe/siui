package com.linln.admin.produce.repository;

import com.linln.admin.produce.domain.CraftParameterRecord;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CraftParameterRecordRepository extends BaseRepository<CraftParameterRecord,Long> {

    @Query(value = "select * from produce_craft_parameter_record where process_task_code = ?1",nativeQuery = true)
    List<CraftParameterRecord> findByProcess_task_code(String processTaskCode);
}
