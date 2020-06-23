package com.linln.admin.base.repository;

import com.linln.admin.base.domain.Process;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author www
 * @date 2020/05/13
 */
public interface ProcessRepository extends BaseRepository<Process, Long> {

    List<Process> findAllByStatus(Byte status);
    @Query(value = "select  * from base_process where id = ?1",nativeQuery = true)
    Process findByPId(Long id);
    @Query(value = "select  top 1 * from base_process where sort_no > ?1 order by sort_no asc ",nativeQuery = true)
    Process moveDown(Integer sort_no);
    @Query(value = "select  top 1 * from base_process where sort_no < ?1 order by sort_no desc ",nativeQuery = true)
    Process moveUp(Integer sort_no);
    @Query(value = "select distinct  process_type from base_process",nativeQuery = true)
    List<String> queryProcessType();
    @Query(value = "select max(sort_no) as maxSortNo from base_process",nativeQuery = true)
    Integer getMaxSortNo();
}