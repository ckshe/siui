package com.linln.admin.maintenance.service.impl;

import com.linln.admin.maintenance.domain.MaintenanceRecords;
import com.linln.admin.maintenance.repository.MaintenanceRecordsRepository;
import com.linln.admin.maintenance.service.MaintenanceRecordsService;
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
public class MaintenanceRecordsServiceImpl implements MaintenanceRecordsService {

    @Autowired
    private MaintenanceRecordsRepository maintenanceRecordsRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public MaintenanceRecords getById(Long id) {
        return maintenanceRecordsRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<MaintenanceRecords> getPageList(Example<MaintenanceRecords> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return maintenanceRecordsRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param maintenanceRecords 实体对象
     */
    @Override
    public MaintenanceRecords save(MaintenanceRecords maintenanceRecords) {
        return maintenanceRecordsRepository.save(maintenanceRecords);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return maintenanceRecordsRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}