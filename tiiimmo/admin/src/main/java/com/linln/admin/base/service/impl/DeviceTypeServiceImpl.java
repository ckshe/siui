package com.linln.admin.base.service.impl;

import com.linln.admin.base.domain.DeviceType;
import com.linln.admin.base.repository.DeviceTypeRepository;
import com.linln.admin.base.service.DeviceTypeService;
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
 * @date 2020/05/14
 */
@Service
public class DeviceTypeServiceImpl implements DeviceTypeService {

    @Autowired
    private DeviceTypeRepository deviceTypeRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public DeviceType getById(Long id) {
        return deviceTypeRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<DeviceType> getPageList(Example<DeviceType> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return deviceTypeRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param deviceType 实体对象
     */
    @Override
    public DeviceType save(DeviceType deviceType) {
        return deviceTypeRepository.save(deviceType);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return deviceTypeRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }

    @Override
    public List<DeviceType> list() {
        return deviceTypeRepository.findAll();
    }
}