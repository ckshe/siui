package com.linln.admin.base.service.impl;

import com.linln.admin.base.domain.DeviceProductElement;
import com.linln.admin.base.repository.DeviceProductElementRepository;
import com.linln.admin.base.service.DeviceProductElementService;
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
 * @author ww
 * @date 2020/06/17
 */
@Service
public class DeviceProductElementServiceImpl implements DeviceProductElementService {

    @Autowired
    private DeviceProductElementRepository deviceProductElementRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public DeviceProductElement getById(Long id) {
        return deviceProductElementRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<DeviceProductElement> getPageList(Example<DeviceProductElement> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return deviceProductElementRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param deviceProductElement 实体对象
     */
    @Override
    public DeviceProductElement save(DeviceProductElement deviceProductElement) {
        return deviceProductElementRepository.save(deviceProductElement);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return deviceProductElementRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}