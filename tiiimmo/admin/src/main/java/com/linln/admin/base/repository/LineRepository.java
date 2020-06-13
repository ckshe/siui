package com.linln.admin.base.repository;

import com.linln.admin.base.domain.Line;
import com.linln.modules.system.repository.BaseRepository;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author ww
 * @date 2020/05/14
 */
public interface LineRepository extends BaseRepository<Line, Long> {
   /* @Query(value = "select bl.id,bl.name, bpa.name from base_line bl " +
            "left join base_plant_area bpa " +
            "on bl.area = bpa.id ",nativeQuery = true)
    <S extends Line> Page<S> findAll(Example<S> var1, Pageable var2);*/
}