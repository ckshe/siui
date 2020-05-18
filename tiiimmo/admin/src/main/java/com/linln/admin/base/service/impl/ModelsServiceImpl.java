package com.linln.admin.base.service.impl;

import com.linln.admin.base.domain.Models;
import com.linln.admin.base.repository.ModelsRepository;
import com.linln.admin.base.service.ModelsService;
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
 * @author 小懒虫
 * @date 2020/05/18
 */
@Service
public class ModelsServiceImpl implements ModelsService {

    @Autowired
    private ModelsRepository modelsRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public Models getById(Long id) {
        return modelsRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<Models> getPageList(Example<Models> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return modelsRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param models 实体对象
     */
    @Override
    public Models save(Models models) {
        return modelsRepository.save(models);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return modelsRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}