package com.linln.admin.produce.service;

import com.linln.admin.produce.domain.PcbCirculation;
import com.linln.common.enums.StatusEnum;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author www
 * @date 2020/05/21
 */
public interface PcbCirculationService {

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<PcbCirculation> getPageList(Example<PcbCirculation> example);

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    PcbCirculation getById(Long id);

    /**
     * 保存数据
     * @param pcbCirculation 实体对象
     */
    PcbCirculation save(PcbCirculation pcbCirculation);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Transactional
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);
}