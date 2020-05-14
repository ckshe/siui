package com.linln.admin.base.service.impl;

import com.linln.admin.base.domain.Solder;
import com.linln.admin.base.repository.SolderRepository;
import com.linln.admin.base.service.SolderService;
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
 * @date 2020/05/14
 */
@Service
public class SolderServiceImpl implements SolderService {

    @Autowired
    private SolderRepository solderRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public Solder getById(Long id) {
        return solderRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<Solder> getPageList(Example<Solder> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return solderRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param solder 实体对象
     */
    @Override
    public Solder save(Solder solder) {
        return solderRepository.save(solder);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return solderRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}