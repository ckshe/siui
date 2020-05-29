package com.linln.admin.produce.repository;

import com.linln.admin.produce.domain.PcbTask;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author 小懒虫
 * @date 2020/05/18
 */
public interface PcbTaskRepository extends BaseRepository<PcbTask, Long> {

    @Query(value = "select * from produce_pcb_task where pcb_task_code = ?1  limit 1 ",nativeQuery = true)
    PcbTask findAllByPcb_task_code(String pcbTaskCode);
}