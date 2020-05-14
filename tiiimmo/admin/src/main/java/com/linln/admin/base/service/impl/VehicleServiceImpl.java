package com.linln.admin.base.service.impl;

import com.linln.admin.base.domain.Vehicle;
import com.linln.admin.base.repository.VehicleRepository;
import com.linln.admin.base.service.VehicleService;
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
 * @date 2020/05/13
 */
@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public Vehicle getById(Long id) {
        return vehicleRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<Vehicle> getPageList(Example<Vehicle> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return vehicleRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param vehicle 实体对象
     */
    @Override
    public Vehicle save(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return vehicleRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}