package com.linln.admin.maintenance.service.impl;

import com.linln.admin.maintenance.domain.CheckRecord;
import com.linln.admin.maintenance.repository.CheckRecordRepository;
import com.linln.admin.maintenance.service.CheckRecordService;
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
 * @date 2020/08/19
 */
@Service
public class CheckRecordServiceImpl implements CheckRecordService {

    @Autowired
    private CheckRecordRepository checkRecordRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public CheckRecord getById(Long id) {
        return checkRecordRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<CheckRecord> getPageList(Example<CheckRecord> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return checkRecordRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param checkRecord 实体对象
     */
    @Override
    public CheckRecord save(CheckRecord checkRecord) {
        return checkRecordRepository.save(checkRecord);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return checkRecordRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}