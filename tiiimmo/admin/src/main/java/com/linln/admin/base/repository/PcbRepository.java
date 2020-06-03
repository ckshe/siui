package com.linln.admin.base.repository;

import com.linln.admin.base.domain.Pcb;
import com.linln.modules.system.repository.BaseRepository;

/**
 * @author www
 * @date 2020/05/13
 */
public interface PcbRepository extends BaseRepository<Pcb, Long> {

    Pcb findByCode(String code);

}