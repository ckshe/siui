package com.linln.admin.base.service.impl;

import com.linln.admin.base.domain.TaskInstruction;
import com.linln.admin.base.repository.TaskInstructionRepository;
import com.linln.admin.base.service.TaskInstructionService;
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
 * @author 连
 * @date 2020/06/09
 */
@Service
public class TaskInstructionServiceImpl implements TaskInstructionService {

    @Autowired
    private TaskInstructionRepository taskInstructionRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public TaskInstruction getById(Long id) {
        return taskInstructionRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<TaskInstruction> getPageList(Example<TaskInstruction> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return taskInstructionRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param taskInstruction 实体对象
     */
    @Override
    public TaskInstruction save(TaskInstruction taskInstruction) {
        return taskInstructionRepository.save(taskInstruction);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return taskInstructionRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}