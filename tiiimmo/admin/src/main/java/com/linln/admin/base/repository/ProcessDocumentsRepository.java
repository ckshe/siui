package com.linln.admin.base.repository;

import com.linln.admin.base.domain.ProcessDocuments;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author www
 * @date 2020/09/14
 */
public interface ProcessDocumentsRepository extends BaseRepository<ProcessDocuments, Long> {

    @Query(value = "select * from base_process_documents where pcb_code = ?1",nativeQuery = true)
    ProcessDocuments findAllByPcbCode(String pcbCode);
}