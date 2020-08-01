package com.linln.admin.base.service.impl;

import com.linln.admin.base.domain.DeviceProductReplaceElement;
import com.linln.admin.base.repository.DeviceProductReplaceElementRepository;
import com.linln.admin.base.service.DeviceProductReplaceElementService;
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
 * @date 2020/08/01
 */
@Service
public class DeviceProductReplaceElementServiceImpl implements DeviceProductReplaceElementService {

    @Autowired
    private DeviceProductReplaceElementRepository deviceProductReplaceElementRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public DeviceProductReplaceElement getById(Long id) {
        return deviceProductReplaceElementRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<DeviceProductReplaceElement> getPageList(Example<DeviceProductReplaceElement> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return deviceProductReplaceElementRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param deviceProductReplaceElement 实体对象
     */
    @Override
    public DeviceProductReplaceElement save(DeviceProductReplaceElement deviceProductReplaceElement) {
        return deviceProductReplaceElementRepository.save(deviceProductReplaceElement);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return deviceProductReplaceElementRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}