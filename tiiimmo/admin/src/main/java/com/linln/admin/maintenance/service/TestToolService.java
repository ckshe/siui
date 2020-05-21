package com.linln.admin.maintenance.service;

import com.linln.admin.maintenance.domain.TestTool;
import com.linln.common.enums.StatusEnum;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 风陵苑主
 * @date 2020/05/21
 */
public interface TestToolService {

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<TestTool> getPageList(Example<TestTool> example);

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    TestTool getById(Long id);

    /**
     * 保存数据
     * @param testTool 实体对象
     */
    TestTool save(TestTool testTool);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Transactional
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);
}