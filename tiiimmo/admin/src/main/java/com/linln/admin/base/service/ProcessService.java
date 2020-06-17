package com.linln.admin.base.service;

import com.linln.admin.base.domain.Process;
import com.linln.common.enums.StatusEnum;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author www
 * @date 2020/05/13
 */
public interface ProcessService {

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<Process> getPageList(Example<Process> example);

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    Process getById(Long id);

    /**
     * 保存数据
     * @param process 实体对象
     */
    Process save(Process process);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Transactional
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);

    /**
     * 下移
     * @param id
     */
    void moveDown(Long id);

    /**
     * 上移
     * @param id
     */
    void moveUp(Long id);

    List<Process> list();

    List<String> queryProcessType();
}