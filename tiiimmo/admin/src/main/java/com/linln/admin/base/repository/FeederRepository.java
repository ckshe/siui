package com.linln.admin.base.repository;

import com.linln.admin.base.domain.Feeder;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author www
 * @date 2020/05/14
 */
public interface FeederRepository extends BaseRepository<Feeder, Long> {

    @Query(value = "update base_feeder set use_times = 0 where id = ?1",nativeQuery = true)
    void updateZero(Long id);
}