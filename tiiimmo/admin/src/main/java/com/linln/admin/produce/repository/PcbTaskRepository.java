package com.linln.admin.produce.repository;

import com.linln.admin.produce.domain.PcbTask;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author 小懒虫
 * @date 2020/05/18
 */
public interface PcbTaskRepository extends BaseRepository<PcbTask, Long> {

    @Query(value = "select * from produce_pcb_task where pcb_task_code = ?1 ",nativeQuery = true)
    List<PcbTask> findAllByPcb_task_code(String pcbTaskCode);

    @Query(value = "SELECT * FROM produce_pcb_task where (produce_plan_complete_date >= ?1 AND produce_plan_complete_date <= ?2 and pcb_task_status != '确认') or (produce_date >= ?1 AND produce_complete_date <= ?2) order by produce_complete_date desc",nativeQuery = true)
    List<PcbTask> findAllByStartEndTime(String startTime,String endTime);

    @Query(value = "SELECT * FROM produce_pcb_task where produce_plan_complete_date >= ?1 AND produce_plan_complete_date <= ?2 order by produce_complete_date desc ",nativeQuery = true)
    List<PcbTask> findAllByStartEndFinishTime(String startTime,String endTime);
}