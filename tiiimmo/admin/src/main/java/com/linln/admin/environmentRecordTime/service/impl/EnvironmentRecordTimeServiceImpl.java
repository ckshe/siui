package com.linln.admin.environmentRecordTime.service.impl;

import com.linln.admin.environmentRecordTime.domain.EnvironmentRecordTime;
import com.linln.admin.environmentRecordTime.repository.EnvironmentRecordTimeRepository;
import com.linln.admin.environmentRecordTime.service.EnvironmentRecordTimeService;
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
 * @date 2020/05/13
 */
@Service
public class EnvironmentRecordTimeServiceImpl implements EnvironmentRecordTimeService {

    @Autowired
    private EnvironmentRecordTimeRepository environmentRecordTimeRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public EnvironmentRecordTime getById(Long id) {
        return environmentRecordTimeRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<EnvironmentRecordTime> getPageList(Example<EnvironmentRecordTime> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return environmentRecordTimeRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param environmentRecordTime 实体对象
     */
    @Override
    public EnvironmentRecordTime save(EnvironmentRecordTime environmentRecordTime) {
        return environmentRecordTimeRepository.save(environmentRecordTime);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return environmentRecordTimeRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}