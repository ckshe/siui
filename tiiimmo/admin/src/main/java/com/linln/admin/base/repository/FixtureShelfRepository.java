package com.linln.admin.base.repository;

import com.linln.admin.base.domain.FixtureShelf;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author 风陵苑主
 * @date 2020/05/14
 */
public interface FixtureShelfRepository extends BaseRepository<FixtureShelf, Long> {
    @Query(value = "select distinct  shelf_type from base_fixture_shelf",nativeQuery = true)
    List<String> queryShelfType();
}