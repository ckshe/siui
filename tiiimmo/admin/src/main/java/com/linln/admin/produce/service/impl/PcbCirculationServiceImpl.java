package com.linln.admin.produce.service.impl;

import com.linln.admin.produce.domain.PcbCirculation;
import com.linln.admin.produce.repository.PcbCirculationRepository;
import com.linln.admin.produce.service.PcbCirculationService;
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
public class PcbCirculationServiceImpl implements PcbCirculationService {

    @Autowired
    private PcbCirculationRepository pcbCirculationRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public PcbCirculation getById(Long id) {
        return pcbCirculationRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<PcbCirculation> getPageList(Example<PcbCirculation> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return pcbCirculationRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param pcbCirculation 实体对象
     */
    @Override
    public PcbCirculation save(PcbCirculation pcbCirculation) {
        return pcbCirculationRepository.save(pcbCirculation);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return pcbCirculationRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}