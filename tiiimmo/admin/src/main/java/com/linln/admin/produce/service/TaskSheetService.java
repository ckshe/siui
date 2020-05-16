package com.linln.admin.produce.service;

import com.linln.admin.produce.domain.TaskSheet;
import com.linln.common.enums.StatusEnum;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 小懒虫
 * @date 2020/05/16
 */
public interface TaskSheetService {

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<TaskSheet> getPageList(Example<TaskSheet> example);

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    TaskSheet getById(Long id);

    /**
     * 保存数据
     * @param taskSheet 实体对象
     */
    TaskSheet save(TaskSheet taskSheet);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Transactional
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);
}