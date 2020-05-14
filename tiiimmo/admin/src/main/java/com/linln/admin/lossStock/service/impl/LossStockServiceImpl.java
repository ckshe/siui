package com.linln.admin.lossStock.service.impl;

import com.linln.admin.lossStock.domain.LossStock;
import com.linln.admin.lossStock.repository.LossStockRepository;
import com.linln.admin.lossStock.service.LossStockService;
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
 * @date 2020/05/13
 */
@Service
public class LossStockServiceImpl implements LossStockService {

    @Autowired
    private LossStockRepository lossStockRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public LossStock getById(Long id) {
        return lossStockRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<LossStock> getPageList(Example<LossStock> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return lossStockRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param lossStock 实体对象
     */
    @Override
    public LossStock save(LossStock lossStock) {
        return lossStockRepository.save(lossStock);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return lossStockRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}