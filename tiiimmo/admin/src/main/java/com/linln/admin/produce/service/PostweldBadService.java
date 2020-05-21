package com.linln.admin.produce.service;

import com.linln.admin.produce.domain.PostweldBad;
import com.linln.common.enums.StatusEnum;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author www
 * @date 2020/05/21
 */
public interface PostweldBadService {

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<PostweldBad> getPageList(Example<PostweldBad> example);

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    PostweldBad getById(Long id);

    /**
     * 保存数据
     * @param postweldBad 实体对象
     */
    PostweldBad save(PostweldBad postweldBad);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Transactional
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);
}