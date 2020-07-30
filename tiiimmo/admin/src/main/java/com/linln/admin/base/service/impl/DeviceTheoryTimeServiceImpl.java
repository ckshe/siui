package com.linln.admin.base.service.impl;

import com.linln.admin.base.domain.DeviceTheoryTime;
import com.linln.admin.base.repository.DeviceTheoryTimeRepository;
import com.linln.admin.base.service.DeviceTheoryTimeService;
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
 * @date 2020/07/30
 */
@Service
public class DeviceTheoryTimeServiceImpl implements DeviceTheoryTimeService {

    @Autowired
    private DeviceTheoryTimeRepository deviceTheoryTimeRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public DeviceTheoryTime getById(Long id) {
        return deviceTheoryTimeRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<DeviceTheoryTime> getPageList(Example<DeviceTheoryTime> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return deviceTheoryTimeRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param deviceTheoryTime 实体对象
     */
    @Override
    public DeviceTheoryTime save(DeviceTheoryTime deviceTheoryTime) {
        return deviceTheoryTimeRepository.save(deviceTheoryTime);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return deviceTheoryTimeRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}