package com.linln.admin.produce.service.impl;

import com.linln.admin.produce.domain.ProcessTheoryTime;
import com.linln.admin.produce.repository.ProcessTheoryTimeRepository;
import com.linln.admin.produce.service.ProcessTheoryTimeService;
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
 * @date 2020/06/13
 */
@Service
public class ProcessTheoryTimeServiceImpl implements ProcessTheoryTimeService {

    @Autowired
    private ProcessTheoryTimeRepository processTheoryTimeRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public ProcessTheoryTime getById(Long id) {
        return processTheoryTimeRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<ProcessTheoryTime> getPageList(Example<ProcessTheoryTime> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return processTheoryTimeRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param processTheoryTime 实体对象
     */
    @Override
    public ProcessTheoryTime save(ProcessTheoryTime processTheoryTime) {
        return processTheoryTimeRepository.save(processTheoryTime);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return processTheoryTimeRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}