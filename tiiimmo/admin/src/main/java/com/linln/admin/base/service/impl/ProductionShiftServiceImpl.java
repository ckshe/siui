package com.linln.admin.base.service.impl;

import com.linln.admin.base.domain.ProductionShift;
import com.linln.admin.base.repository.ProductionShiftRepository;
import com.linln.admin.base.service.ProductionShiftService;
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
 * @date 2020/06/12
 */
@Service
public class ProductionShiftServiceImpl implements ProductionShiftService {

    @Autowired
    private ProductionShiftRepository productionShiftRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public ProductionShift getById(Long id) {
        return productionShiftRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<ProductionShift> getPageList(Example<ProductionShift> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return productionShiftRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param productionShift 实体对象
     */
    @Override
    public ProductionShift save(ProductionShift productionShift) {
        return productionShiftRepository.save(productionShift);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return productionShiftRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}