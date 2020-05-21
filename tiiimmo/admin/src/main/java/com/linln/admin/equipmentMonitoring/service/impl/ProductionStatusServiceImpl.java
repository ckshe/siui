package com.linln.admin.equipmentMonitoring.service.impl;

import com.linln.admin.equipmentMonitoring.domain.ProductionStatus;
import com.linln.admin.equipmentMonitoring.repository.ProductionStatusRepository;
import com.linln.admin.equipmentMonitoring.service.ProductionStatusService;
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
public class ProductionStatusServiceImpl implements ProductionStatusService {

    @Autowired
    private ProductionStatusRepository productionStatusRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public ProductionStatus getById(Long id) {
        return productionStatusRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<ProductionStatus> getPageList(Example<ProductionStatus> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return productionStatusRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param productionStatus 实体对象
     */
    @Override
    public ProductionStatus save(ProductionStatus productionStatus) {
        return productionStatusRepository.save(productionStatus);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return productionStatusRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}