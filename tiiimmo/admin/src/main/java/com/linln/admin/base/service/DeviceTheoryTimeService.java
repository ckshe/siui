package com.linln.admin.base.service;

import com.linln.admin.base.domain.DeviceTheoryTime;
import com.linln.common.enums.StatusEnum;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ww
 * @date 2020/07/30
 */
public interface DeviceTheoryTimeService {

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<DeviceTheoryTime> getPageList(Example<DeviceTheoryTime> example);

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    DeviceTheoryTime getById(Long id);

    /**
     * 保存数据
     * @param deviceTheoryTime 实体对象
     */
    DeviceTheoryTime save(DeviceTheoryTime deviceTheoryTime);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Transactional
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);
}