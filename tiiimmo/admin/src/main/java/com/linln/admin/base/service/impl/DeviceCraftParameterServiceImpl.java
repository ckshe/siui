package com.linln.admin.base.service.impl;

import com.linln.admin.base.domain.DeviceCraftParameter;
import com.linln.admin.base.repository.DeviceCraftParameterRepository;
import com.linln.admin.base.service.DeviceCraftParameterService;
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
 * @date 2020/08/18
 */
@Service
public class DeviceCraftParameterServiceImpl implements DeviceCraftParameterService {

    @Autowired
    private DeviceCraftParameterRepository deviceCraftParameterRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public DeviceCraftParameter getById(Long id) {
        return deviceCraftParameterRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<DeviceCraftParameter> getPageList(Example<DeviceCraftParameter> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return deviceCraftParameterRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param deviceCraftParameter 实体对象
     */
    @Override
    public DeviceCraftParameter save(DeviceCraftParameter deviceCraftParameter) {
        return deviceCraftParameterRepository.save(deviceCraftParameter);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return deviceCraftParameterRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}