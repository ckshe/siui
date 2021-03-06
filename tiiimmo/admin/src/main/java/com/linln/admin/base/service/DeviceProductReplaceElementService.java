package com.linln.admin.base.service;

import com.linln.admin.base.domain.DeviceProductReplaceElement;
import com.linln.common.enums.StatusEnum;
import com.linln.common.vo.ResultVo;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ww
 * @date 2020/08/01
 */
public interface DeviceProductReplaceElementService {

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<DeviceProductReplaceElement> getPageList(Example<DeviceProductReplaceElement> example);

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    DeviceProductReplaceElement getById(Long id);

    /**
     * 保存数据
     * @param deviceProductReplaceElement 实体对象
     */
    DeviceProductReplaceElement save(DeviceProductReplaceElement deviceProductReplaceElement);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Transactional
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);

    // _______________________________________________________________________________

    DeviceProductReplaceElement queryByOriginalProductCode(String original_product_code);

    ResultVo queryByReplaceProductCode(String replaceProductCode);

    //ResultVo queryByReplaceProductCode(DeviceProductReplaceElementReq req);
}