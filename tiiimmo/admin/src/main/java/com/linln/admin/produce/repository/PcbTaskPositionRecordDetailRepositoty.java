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

    @Query(value = "SELECT * FROM produce_task_position_record_detail WHERE device_code = ?1 and install_status = '1'",nativeQuery = true)
    List<PcbTaskPositionRecordDetail> findByDevice_code(String deviceCode);

    @Query(value = "SELECT * FROM produce_task_position_record_detail WHERE process_task_code = ?1 and product_code = ?2 and device_code = ?3",nativeQuery = true)
    PcbTaskPositionRecordDetail findByProcess_task_codeAndProduct_code(String processTaskCode,String productCode,String deviceCode);

    @Query(value = "SELECT * FROM produce_task_position_record_detail WHERE process_task_code = ?1 ",nativeQuery = true)
    List<PcbTaskPositionRecordDetail> findByProcess_task_code(String processTaskCode);

    @Query(value = "SELECT * FROM produce_task_position_record_detail WHERE process_task_code = ?1 and device_code = ?2 order by position",nativeQuery = true)
    List<PcbTaskPositionRecordDetail> findByProcess_task_codeAndDevice_code(String processTaskCode, String deviceCode);

    @Query(value = "SELECT * FROM produce_task_position_record_detail WHERE product_code = ?1 ",nativeQuery = true)
    PcbTaskPositionRecordDetail findByProduct_code(String productCode);
}
