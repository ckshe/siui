package com.linln.admin.base.repository;

import com.linln.admin.base.domain.OperationManual;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author 连
 * @date 2020/06/10
 */
public interface OperationManualRepository extends JpaRepository<OperationManual, Long> {
    @Query(value = "select * from base_operation_manual um where um.user_id=?1",nativeQuery = true)
    List<OperationManual> findByUserId(Integer userId);

    @Query(value = "select * from base_operation_manual um where um.mould_code like CONCAT('%',?1,'%')",nativeQuery = true)
    List<OperationManual> findByMouldSerial(String mouldSerial);


    @Query(value = "select count(*) from base_operation_manual um where um.image like CONCAT('%',?1,'%') ",nativeQuery = true)
    int countWithImage(String image);


}