package com.linln.admin.base.service.impl;

import com.linln.admin.base.domain.Substitutes;
import com.linln.admin.base.repository.SubstitutesRepository;
import com.linln.admin.base.service.SubstitutesService;
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
 * @author 连
 * @date 2020/06/09
 */
@Service
public class SubstitutesServiceImpl implements SubstitutesService {

    @Autowired
    private SubstitutesRepository substitutesRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public Substitutes getById(Long id) {
        return substitutesRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<Substitutes> getPageList(Example<Substitutes> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return substitutesRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param substitutes 实体对象
     */
    @Override
    public Substitutes save(Substitutes substitutes) {
        return substitutesRepository.save(substitutes);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return substitutesRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}