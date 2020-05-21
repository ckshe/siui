package com.linln.admin.maintenance.service.impl;

import com.linln.admin.maintenance.domain.MaintenanceTable;
import com.linln.admin.maintenance.repository.MaintenanceTableRepository;
import com.linln.admin.maintenance.service.MaintenanceTableService;
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
public class MaintenanceTableServiceImpl implements MaintenanceTableService {

    @Autowired
    private MaintenanceTableRepository maintenanceTableRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public MaintenanceTable getById(Long id) {
        return maintenanceTableRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<MaintenanceTable> getPageList(Example<MaintenanceTable> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return maintenanceTableRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param maintenanceTable 实体对象
     */
    @Override
    public MaintenanceTable save(MaintenanceTable maintenanceTable) {
        return maintenanceTableRepository.save(maintenanceTable);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return maintenanceTableRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}