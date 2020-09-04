package com.linln.admin.base.service;

import com.linln.RespAndReqs.DeviceReq;
import com.linln.admin.base.domain.Device;
import com.linln.common.enums.StatusEnum;
import com.linln.common.vo.ResultVo;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author www
 * @date 2020/05/14
 */
public interface DeviceService {

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<Device> getPageList(Example<Device> example);

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    Device getById(Long id);

    /**
     * 保存数据
     * @param device 实体对象
     */
    Device save(Device device);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Transactional
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);


    ResultVo getDeviceByProcess(DeviceReq req);

    ResultVo getDeviceByProcessType();

    List<Device> list();

}