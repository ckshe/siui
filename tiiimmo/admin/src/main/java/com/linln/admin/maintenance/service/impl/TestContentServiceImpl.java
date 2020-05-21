package com.linln.admin.maintenance.service.impl;

import com.linln.admin.maintenance.domain.TestContent;
import com.linln.admin.maintenance.repository.TestContentRepository;
import com.linln.admin.maintenance.service.TestContentService;
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
public class TestContentServiceImpl implements TestContentService {

    @Autowired
    private TestContentRepository testContentRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public TestContent getById(Long id) {
        return testContentRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<TestContent> getPageList(Example<TestContent> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return testContentRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param testContent 实体对象
     */
    @Override
    public TestContent save(TestContent testContent) {
        return testContentRepository.save(testContent);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return testContentRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}