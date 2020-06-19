package com.linln.admin.base.repository;

import com.linln.admin.base.domain.BadNews;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author www
 * @date 2020/05/13
 */
public interface BadNewsRepository extends BaseRepository<BadNews, Long> {

    @Query(value = "select * from base_bad_news where bad_type = ?1  " ,nativeQuery = true)
    List<BadNews> findBybadType(String badType);


}