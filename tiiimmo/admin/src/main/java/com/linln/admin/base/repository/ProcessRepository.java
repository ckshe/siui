package com.linln.admin.base.repository;

import com.linln.admin.base.domain.Process;
import com.linln.modules.system.repository.BaseRepository;

import java.util.List;

/**
 * @author www
 * @date 2020/05/13
 */
public interface ProcessRepository extends BaseRepository<Process, Long> {

    List<Process> findAllByStatus(Byte status);
}