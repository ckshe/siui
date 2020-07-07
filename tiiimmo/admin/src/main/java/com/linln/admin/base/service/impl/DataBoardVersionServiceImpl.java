package com.linln.admin.base.service.impl;

import com.linln.admin.base.domain.DataBoardVersion;
import com.linln.admin.base.repository.DataBoardVersionRepository;
import com.linln.admin.base.service.DataBoardVersionService;
import com.linln.common.data.PageSort;
import com.linln.common.enums.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 风陵苑主
 * @date 2020/07/07
 */
@Service
public class DataBoardVersionServiceImpl implements DataBoardVersionService {

    @Autowired
    private DataBoardVersionRepository dataBoardVersionRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public DataBoardVersion getById(Long id) {
        return dataBoardVersionRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<DataBoardVersion> getPageList(Example<DataBoardVersion> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return dataBoardVersionRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param dataBoardVersion 实体对象
     */
    @Override
    public DataBoardVersion save(DataBoardVersion dataBoardVersion) {
        return dataBoardVersionRepository.save(dataBoardVersion);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return dataBoardVersionRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}