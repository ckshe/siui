package com.linln.admin.base.repository;

import com.linln.admin.base.domain.Shelves;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author 风陵苑主
 * @date 2020/05/14
 */
public interface ShelvesRepository extends BaseRepository<Shelves, Long> {
    @Query(value = "select * from base_shelves where num = ?1",nativeQuery = true)
    Shelves queryByshelvesNo(String shelvesNo);
}