package com.linln.admin.base.repository;

import com.linln.admin.base.domain.Program;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author 连
 * @date 2020/06/09
 */
public interface ProgramRepository extends BaseRepository<Program, Long> {

    @Query(value = "select DISTINCT face from base_program",nativeQuery = true)
    List<String> findFace();

}