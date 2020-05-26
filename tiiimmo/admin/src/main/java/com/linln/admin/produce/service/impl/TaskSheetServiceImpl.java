package com.linln.admin.produce.service.impl;

import com.linln.admin.produce.domain.TaskSheet;
import com.linln.admin.produce.repository.TaskSheetRepository;
import com.linln.admin.produce.service.TaskSheetService;
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
 * @author 小懒虫
 * @date 2020/05/16
 */
@Service
public class TaskSheetServiceImpl implements TaskSheetService {

    @Autowired
    private TaskSheetRepository taskSheetRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public TaskSheet getById(Long id) {
        return taskSheetRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<TaskSheet> getPageList(Example<TaskSheet> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return taskSheetRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param taskSheet 实体对象
     */
    @Override
    public TaskSheet save(TaskSheet taskSheet) {
        return taskSheetRepository.save(taskSheet);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return taskSheetRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }

}