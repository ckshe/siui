package com.linln.admin.equipmentMonitoring.service;

import com.linln.admin.equipmentMonitoring.domain.ProductionStatus;
import com.linln.common.enums.StatusEnum;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 风陵苑主
 * @date 2020/05/21
 */
public interface ProductionStatusService {

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<ProductionStatus> getPageList(Example<ProductionStatus> example);

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    ProductionStatus getById(Long id);

    /**
     * 保存数据
     * @param productionStatus 实体对象
     */
    ProductionStatus save(ProductionStatus productionStatus);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Transactional
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);
}