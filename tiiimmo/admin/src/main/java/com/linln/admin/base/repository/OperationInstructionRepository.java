package com.linln.admin.base.repository;

import com.linln.admin.base.domain.OperationInstruction;
import com.linln.modules.system.repository.BaseRepository;

/**
 * @author 连
 * @date 2020/06/09
 */
public interface OperationInstructionRepository extends BaseRepository<OperationInstruction, Long> {

    OperationInstruction findByCode(String code);


}