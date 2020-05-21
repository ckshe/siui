package com.linln.admin.maintenance.service.impl;

import com.linln.admin.maintenance.domain.SpareParts;
import com.linln.admin.maintenance.repository.SparePartsRepository;
import com.linln.admin.maintenance.service.SparePartsService;
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
 * @author 风陵苑主
 * @date 2020/05/21
 */
@Service
public class SparePartsServiceImpl implements SparePartsService {

    @Autowired
    private SparePartsRepository sparePartsRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public SpareParts getById(Long id) {
        return sparePartsRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<SpareParts> getPageList(Example<SpareParts> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return sparePartsRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param spareParts 实体对象
     */
    @Override
    public SpareParts save(SpareParts spareParts) {
        return sparePartsRepository.save(spareParts);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return sparePartsRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}