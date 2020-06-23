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
}