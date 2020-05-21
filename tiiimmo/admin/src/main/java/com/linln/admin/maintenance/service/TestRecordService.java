package com.linln.admin.maintenance.service;

import com.linln.admin.maintenance.domain.TestRecord;
import com.linln.common.enums.StatusEnum;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 风陵苑主
 * @date 2020/05/21
 */
public interface TestRecordService {

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<TestRecord> getPageList(Example<TestRecord> example);

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    TestRecord getById(Long id);

    /**
     * 保存数据
     * @param testRecord 实体对象
     */
    TestRecord save(TestRecord testRecord);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Transactional
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);
}