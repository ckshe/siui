package com.linln.admin.produce.repository;

import com.linln.admin.produce.domain.PCBPlateNo;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

public interface PCBPlateNoRepository extends BaseRepository<PCBPlateNo,Long> {

    @Query(value = "select  * from produce_pcb_plate_no where  pcb_code = ?1",nativeQuery = true)
    PCBPlateNo findByPcb_code(String code);
}
