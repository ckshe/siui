package com.linln.admin.produce.service.impl;

import com.linln.admin.produce.domain.PatchBad;
import com.linln.admin.produce.repository.PatchBadRepository;
import com.linln.admin.produce.service.PatchBadService;
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
 * @author www
 * @date 2020/05/21
 */
@Service
public class PatchBadServiceImpl implements PatchBadService {

    @Autowired
    private PatchBadRepository patchBadRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public PatchBad getById(Long id) {
        return patchBadRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<PatchBad> getPageList(Example<PatchBad> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return patchBadRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param patchBad 实体对象
     */
    @Override
    public PatchBad save(PatchBad patchBad) {
        return patchBadRepository.save(patchBad);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return patchBadRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}