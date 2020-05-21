package com.linln.admin.project.service.impl;

import com.linln.admin.project.domain.PcbTechnology;
import com.linln.admin.project.repository.PcbTechnologyRepository;
import com.linln.admin.project.service.PcbTechnologyService;
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
public class PcbTechnologyServiceImpl implements PcbTechnologyService {

    @Autowired
    private PcbTechnologyRepository pcbTechnologyRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public PcbTechnology getById(Long id) {
        return pcbTechnologyRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<PcbTechnology> getPageList(Example<PcbTechnology> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return pcbTechnologyRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param pcbTechnology 实体对象
     */
    @Override
    public PcbTechnology save(PcbTechnology pcbTechnology) {
        return pcbTechnologyRepository.save(pcbTechnology);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return pcbTechnologyRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}