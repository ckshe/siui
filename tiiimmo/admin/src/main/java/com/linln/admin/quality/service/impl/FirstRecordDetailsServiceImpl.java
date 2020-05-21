package com.linln.admin.quality.service.impl;

import com.linln.admin.quality.domain.FirstRecordDetails;
import com.linln.admin.quality.repository.FirstRecordDetailsRepository;
import com.linln.admin.quality.service.FirstRecordDetailsService;
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
public class FirstRecordDetailsServiceImpl implements FirstRecordDetailsService {

    @Autowired
    private FirstRecordDetailsRepository firstRecordDetailsRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public FirstRecordDetails getById(Long id) {
        return firstRecordDetailsRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<FirstRecordDetails> getPageList(Example<FirstRecordDetails> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return firstRecordDetailsRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param firstRecordDetails 实体对象
     */
    @Override
    public FirstRecordDetails save(FirstRecordDetails firstRecordDetails) {
        return firstRecordDetailsRepository.save(firstRecordDetails);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return firstRecordDetailsRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}