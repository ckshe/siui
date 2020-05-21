package com.linln.admin.maintenance.service.impl;

import com.linln.admin.maintenance.domain.TestTool;
import com.linln.admin.maintenance.repository.TestToolRepository;
import com.linln.admin.maintenance.service.TestToolService;
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
public class TestToolServiceImpl implements TestToolService {

    @Autowired
    private TestToolRepository testToolRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public TestTool getById(Long id) {
        return testToolRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<TestTool> getPageList(Example<TestTool> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return testToolRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param testTool 实体对象
     */
    @Override
    public TestTool save(TestTool testTool) {
        return testToolRepository.save(testTool);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return testToolRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}