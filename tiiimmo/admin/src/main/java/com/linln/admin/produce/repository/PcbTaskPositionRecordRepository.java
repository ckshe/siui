package com.linln.admin.produce.repository;

import com.linln.admin.produce.domain.PcbTaskPositionRecord;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

public interface PcbTaskPositionRecordRepository extends BaseRepository<PcbTaskPositionRecord,Long> {

    @Query(value = "select * from produce_task_position_record where pcb_task_code = ?1",nativeQuery = true)
    PcbTaskPositionRecord findByPcb_task_code(String pcbTaskCode);
    @Query(value = "select * from produce_task_position_record where process_task_code = ?1 and device_code = ?2",nativeQuery = true)
    PcbTaskPositionRecord findByProcess_task_code(String processTaskCode,String deviceCode);
}
