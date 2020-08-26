package com.linln.admin.base.repository;

import com.linln.admin.base.domain.ESOP;
import com.linln.admin.base.domain.OperationInstruction;
import com.linln.modules.system.repository.BaseRepository;

/**
 * @author w
 * @date 2020/06/09
 */
public interface ESOPRepository extends BaseRepository<ESOP, Long> {

    ESOP findByCode(String code);

    ESOP findByPcbCode(String pcbCode);


}