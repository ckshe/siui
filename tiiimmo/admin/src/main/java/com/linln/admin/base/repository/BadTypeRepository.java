package com.linln.admin.base.repository;

import com.linln.admin.base.domain.BadType;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author www
 * @date 2020/05/13
 */
public interface BadTypeRepository extends BaseRepository<BadType, Long> {

    @Query(value = "select * from base_bad_type where bad_class = ?1",nativeQuery = true)
    List<BadType> findByBadClass(String badClass);
}