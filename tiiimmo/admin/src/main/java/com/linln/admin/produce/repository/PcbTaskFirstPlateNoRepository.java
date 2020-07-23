package com.linln.admin.produce.repository;

import com.linln.admin.produce.domain.PcbTaskFirstPlateNo;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

public interface PcbTaskFirstPlateNoRepository extends BaseRepository<PcbTaskFirstPlateNo,Long> {


    @Query(value = "select * from produce_pcbtask_first_plate_no where pcb_task_code =?1",nativeQuery = true)
    PcbTaskFirstPlateNo findByPcb_task_code(String pcbTaksCode);

}
