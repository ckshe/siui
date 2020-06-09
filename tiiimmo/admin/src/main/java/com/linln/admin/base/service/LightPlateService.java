package com.linln.admin.base.service;

import com.linln.admin.base.domain.LightPlate;
import com.linln.common.enums.StatusEnum;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 连
 * @date 2020/06/09
 */
public interface LightPlateService {

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<LightPlate> getPageList(Example<LightPlate> example);

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    LightPlate getById(Long id);

    /**
     * 保存数据
     * @param lightPlate 实体对象
     */
    LightPlate save(LightPlate lightPlate);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Transactional
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);
}