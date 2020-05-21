package com.linln.admin.project.service;

import com.linln.admin.project.domain.MouldTechnology;
import com.linln.common.enums.StatusEnum;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author www
 * @date 2020/05/21
 */
public interface MouldTechnologyService {

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<MouldTechnology> getPageList(Example<MouldTechnology> example);

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    MouldTechnology getById(Long id);

    /**
     * 保存数据
     * @param mouldTechnology 实体对象
     */
    MouldTechnology save(MouldTechnology mouldTechnology);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Transactional
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);
}