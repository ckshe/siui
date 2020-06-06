package com.linln.admin.base.repository;

import com.linln.admin.base.domain.Skill;
import com.linln.modules.system.repository.BaseRepository;
import net.bytebuddy.asm.Advice;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author www
 * @date 2020/05/31
 */
public interface SkillRepository extends BaseRepository<Skill, Long> {

    @Query(value = "SELECT * FROM base_skill WHERE id IN (?1)",nativeQuery = true)
    List<Skill> findAllByIdIn(List<Long> ids);
}