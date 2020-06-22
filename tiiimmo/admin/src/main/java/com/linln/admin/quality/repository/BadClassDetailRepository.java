package com.linln.admin.quality.repository;

import com.linln.admin.quality.domain.BadClassDetail;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BadClassDetailRepository extends BaseRepository<BadClassDetail,Long> {

    @Query(value = "select * from quality_badclass_detail where pcb_task_code = ?1",nativeQuery = true)
    List<BadClassDetail> findByPcb_task_code(String pcbTaskCode);
    @Query(value = "select * from quality_badclass_detail where plate_no = ?1",nativeQuery = true)
    List<BadClassDetail> findByPlate_no(String plateNo);
}