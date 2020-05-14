package com.linln.admin.pda.service.impl;

import com.linln.admin.pda.domain.Pda;
import com.linln.admin.pda.repository.PdaRepository;
import com.linln.admin.pda.service.PdaService;
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
public class PdaServiceImpl implements PdaService {

    @Autowired
    private PdaRepository pdaRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public Pda getById(Long id) {
        return pdaRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<Pda> getPageList(Example<Pda> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return pdaRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param pda 实体对象
     */
    @Override
    public Pda save(Pda pda) {
        return pdaRepository.save(pda);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return pdaRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}