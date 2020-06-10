package com.linln.admin.base.service.impl;

import com.linln.admin.base.domain.Spares;
import com.linln.admin.base.repository.SparesRepository;
import com.linln.admin.base.service.SparesService;
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
 * @author 连
 * @date 2020/06/10
 */
@Service
public class SparesServiceImpl implements SparesService {

    @Autowired
    private SparesRepository sparesRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public Spares getById(Long id) {
        return sparesRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<Spares> getPageList(Example<Spares> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return sparesRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param spares 实体对象
     */
    @Override
    public Spares save(Spares spares) {
        return sparesRepository.save(spares);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return sparesRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}