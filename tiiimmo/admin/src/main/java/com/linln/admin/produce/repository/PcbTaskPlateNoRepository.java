package com.linln.admin.produce.repository;

import com.linln.admin.produce.domain.PcbTaskPlateNo;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PcbTaskPlateNoRepository extends BaseRepository<PcbTaskPlateNo,Long> {


    @Query(value = "SELECT * FROM produce_pcbtask_plate_no where pcb_task_code = ?1 ORDER BY is_count desc,id ",nativeQuery = true)
    List<PcbTaskPlateNo> findByPcb_task_code(String pcbTaskCode);
    @Query(value = "select  * from produce_pcbtask_plate_no where plate_no = ?1",nativeQuery = true)
    PcbTaskPlateNo findByPlate_no(String plateNo);
}
