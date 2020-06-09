package com.linln.admin.base.service.impl;

import com.linln.admin.base.domain.OperationInstruction;
import com.linln.admin.base.repository.OperationInstructionRepository;
import com.linln.admin.base.service.OperationInstructionService;
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
public class OperationInstructionServiceImpl implements OperationInstructionService {

    @Autowired
    private OperationInstructionRepository operationInstructionRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public OperationInstruction getById(Long id) {
        return operationInstructionRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<OperationInstruction> getPageList(Example<OperationInstruction> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return operationInstructionRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param operationInstruction 实体对象
     */
    @Override
    public OperationInstruction save(OperationInstruction operationInstruction) {
        return operationInstructionRepository.save(operationInstruction);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return operationInstructionRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}