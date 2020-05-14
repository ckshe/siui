package com.linln.admin.base.service.impl;

import com.linln.admin.base.domain.Process;
import com.linln.admin.base.repository.ProcessRepository;
import com.linln.admin.base.service.ProcessService;
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
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    private ProcessRepository processRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public Process getById(Long id) {
        return processRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<Process> getPageList(Example<Process> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return processRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param process 实体对象
     */
    @Override
    public Process save(Process process) {
        return processRepository.save(process);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return processRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}