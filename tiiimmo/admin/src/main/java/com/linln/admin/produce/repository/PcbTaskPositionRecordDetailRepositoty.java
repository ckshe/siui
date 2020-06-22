package com.linln.admin.produce.repository;

import com.linln.admin.produce.domain.PcbTaskPositionRecordDetail;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PcbTaskPositionRecordDetailRepositoty extends BaseRepository<PcbTaskPositionRecordDetail, Long> {

    @Query(value = "SELECT * FROM produce_task_position_record_detail WHERE pcb_task_code = ?1 ",nativeQuery = true)
    List<PcbTaskPositionRecordDetail> findByPcb_task_code(String pcbTaskCode);

    @Query(value = "SELECT * FROM produce_task_position_record_detail WHERE pcb_task_code = ?1 and product_code = ?2",nativeQuery = true)
    PcbTaskPositionRecordDetail findByPcb_task_codeAndProduct_code(String pcbTaskCode,String productCode);

}
