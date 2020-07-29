package com.linln.admin.base.service;

import com.linln.RespAndReqs.DeviceProductElementReq;
import com.linln.admin.base.domain.DeviceProductElement;
import com.linln.common.enums.StatusEnum;
import com.linln.common.vo.ResultVo;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author ww
 * @date 2020/06/17
 */
public interface DeviceProductElementService {

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<DeviceProductElement> getPageList(Example<DeviceProductElement> example);

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    DeviceProductElement getById(Long id);

    /**
     * 保存数据
     * @param deviceProductElement 实体对象
     */
    DeviceProductElement save(DeviceProductElement deviceProductElement);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Transactional
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);


    ResultVo importCommonExcel(MultipartFile file);

    ResultVo findElement(DeviceProductElementReq req);
}