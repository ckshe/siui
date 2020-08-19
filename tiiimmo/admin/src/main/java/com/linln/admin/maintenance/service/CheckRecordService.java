package com.linln.admin.maintenance.service;

import com.linln.admin.maintenance.domain.CheckRecord;
import com.linln.common.enums.StatusEnum;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lian
 * @date 2020/08/19
 */
public interface CheckRecordService {

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<CheckRecord> getPageList(Example<CheckRecord> example);

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    CheckRecord getById(Long id);

    /**
     * 保存数据
     * @param checkRecord 实体对象
     */
    CheckRecord save(CheckRecord checkRecord);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Transactional
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);
}