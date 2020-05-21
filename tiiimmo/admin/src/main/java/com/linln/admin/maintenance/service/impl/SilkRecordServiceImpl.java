package com.linln.admin.maintenance.service.impl;

import com.linln.admin.maintenance.domain.SilkRecord;
import com.linln.admin.maintenance.repository.SilkRecordRepository;
import com.linln.admin.maintenance.service.SilkRecordService;
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
 * @author 风陵苑主
 * @date 2020/05/21
 */
@Service
public class SilkRecordServiceImpl implements SilkRecordService {

    @Autowired
    private SilkRecordRepository silkRecordRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public SilkRecord getById(Long id) {
        return silkRecordRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<SilkRecord> getPageList(Example<SilkRecord> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return silkRecordRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param silkRecord 实体对象
     */
    @Override
    public SilkRecord save(SilkRecord silkRecord) {
        return silkRecordRepository.save(silkRecord);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return silkRecordRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}