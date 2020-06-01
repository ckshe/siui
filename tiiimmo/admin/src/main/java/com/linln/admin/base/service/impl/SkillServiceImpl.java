package com.linln.admin.base.service.impl;

import com.linln.admin.base.domain.Skill;
import com.linln.admin.base.repository.SkillRepository;
import com.linln.admin.base.service.SkillService;
import com.linln.common.data.PageSort;
import com.linln.common.enums.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author www
 * @date 2020/05/31
 */
@Service
public class SkillServiceImpl implements SkillService {

    @Autowired
    private SkillRepository skillRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public Skill getById(Long id) {
        return skillRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<Skill> getPageList(Example<Skill> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return skillRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param skill 实体对象
     */
    @Override
    public Skill save(Skill skill) {
        return skillRepository.save(skill);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return skillRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}