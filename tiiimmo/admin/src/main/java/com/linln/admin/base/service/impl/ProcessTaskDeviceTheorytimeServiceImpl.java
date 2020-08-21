package com.linln.admin.base.service.impl;

import com.linln.admin.base.domain.ProcessTaskDeviceTheorytime;
import com.linln.admin.base.repository.ProcessTaskDeviceTheorytimeRepository;
import com.linln.admin.base.service.ProcessTaskDeviceTheorytimeService;
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
 * @author lian
 * @date 2020/08/20
 */
@Service
public class ProcessTaskDeviceTheorytimeServiceImpl implements ProcessTaskDeviceTheorytimeService {

    @Autowired
    private ProcessTaskDeviceTheorytimeRepository processTaskDeviceTheorytimeRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public ProcessTaskDeviceTheorytime getById(Long id) {
        return processTaskDeviceTheorytimeRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<ProcessTaskDeviceTheorytime> getPageList(Example<ProcessTaskDeviceTheorytime> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return processTaskDeviceTheorytimeRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param processTaskDeviceTheorytime 实体对象
     */
    @Override
    public ProcessTaskDeviceTheorytime save(ProcessTaskDeviceTheorytime processTaskDeviceTheorytime) {
        return processTaskDeviceTheorytimeRepository.save(processTaskDeviceTheorytime);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return processTaskDeviceTheorytimeRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}