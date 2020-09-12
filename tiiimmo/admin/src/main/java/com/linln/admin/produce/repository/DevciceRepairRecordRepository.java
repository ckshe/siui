package com.linln.admin.produce.repository;

import com.linln.admin.produce.domain.DevciceRepairRecord;
import com.linln.modules.system.repository.BaseRepository;

import java.util.List;

public interface DevciceRepairRecordRepository extends BaseRepository<DevciceRepairRecord,Long> {

    List<DevciceRepairRecord> findAllByYear(String year);
}
