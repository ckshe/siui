package com.linln.admin.base.repository;

import com.linln.admin.base.domain.Process;
import com.linln.modules.system.repository.BaseRepository;
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
    @Query(value = "select  * from base_process where sort_no = ?1",nativeQuery = true)
    Process moveDown(Integer sort_no);
    @Query(value = "select  * from base_process where sort_no = ?1",nativeQuery = true)
    Process moveUp(Integer sort_no);

}