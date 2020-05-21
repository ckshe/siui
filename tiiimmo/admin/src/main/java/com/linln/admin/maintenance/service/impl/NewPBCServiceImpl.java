package com.linln.admin.maintenance.service.impl;

import com.linln.admin.maintenance.domain.NewPBC;
import com.linln.admin.maintenance.repository.NewPBCRepository;
import com.linln.admin.maintenance.service.NewPBCService;
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
 * @date 2020/05/21
 */
@Service
public class NewPBCServiceImpl implements NewPBCService {

    @Autowired
    private NewPBCRepository newPBCRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public NewPBC getById(Long id) {
        return newPBCRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<NewPBC> getPageList(Example<NewPBC> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return newPBCRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param newPBC 实体对象
     */
    @Override
    public NewPBC save(NewPBC newPBC) {
        return newPBCRepository.save(newPBC);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return newPBCRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}