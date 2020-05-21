package com.linln.admin.project.service.impl;

import com.linln.admin.project.domain.MouldTechnology;
import com.linln.admin.project.repository.MouldTechnologyRepository;
import com.linln.admin.project.service.MouldTechnologyService;
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
 * @date 2020/05/21
 */
@Service
public class MouldTechnologyServiceImpl implements MouldTechnologyService {

    @Autowired
    private MouldTechnologyRepository mouldTechnologyRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public MouldTechnology getById(Long id) {
        return mouldTechnologyRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<MouldTechnology> getPageList(Example<MouldTechnology> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return mouldTechnologyRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param mouldTechnology 实体对象
     */
    @Override
    public MouldTechnology save(MouldTechnology mouldTechnology) {
        return mouldTechnologyRepository.save(mouldTechnology);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return mouldTechnologyRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}