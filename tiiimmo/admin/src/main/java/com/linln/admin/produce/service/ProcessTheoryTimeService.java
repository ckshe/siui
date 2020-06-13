package com.linln.admin.produce.service;

import com.linln.admin.produce.domain.ProcessTheoryTime;
import com.linln.common.enums.StatusEnum;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ww
 * @date 2020/06/13
 */
public interface ProcessTheoryTimeService {

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<ProcessTheoryTime> getPageList(Example<ProcessTheoryTime> example);

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    ProcessTheoryTime getById(Long id);

    /**
     * 保存数据
     * @param processTheoryTime 实体对象
     */
    ProcessTheoryTime save(ProcessTheoryTime processTheoryTime);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Transactional
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);
}