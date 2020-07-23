package com.linln.admin.base.repository;

import com.linln.admin.base.domain.ScannerProcessTask;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScannerProcessTaskRepository extends BaseRepository<ScannerProcessTask,Long> {

    @Query(value = "select * from base_scanner_process_task where device_sort = ?1 ",nativeQuery = true)
    ScannerProcessTask findBydevicesort(Integer deviceSort);
}
