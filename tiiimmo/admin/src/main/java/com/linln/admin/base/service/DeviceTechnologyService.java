package com.linln.admin.base.service;

import com.linln.admin.base.domain.DeviceTechnology;
import com.linln.common.enums.StatusEnum;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author www
 * @date 2020/05/14
 */
public interface DeviceTechnologyService {

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<DeviceTechnology> getPageList(Example<DeviceTechnology> example);

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    DeviceTechnology getById(Long id);

    /**
     * 保存数据
     * @param deviceTechnology 实体对象
     */
    DeviceTechnology save(DeviceTechnology deviceTechnology);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Transactional
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);

    List<DeviceTechnology> list();

    List<String> queryDeviceTechnologyCode();
}