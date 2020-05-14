package com.linln.admin.base.service.impl;

import com.linln.admin.base.domain.Pcbdetail;
import com.linln.admin.base.repository.PcbdetailRepository;
import com.linln.admin.base.service.PcbdetailService;
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
 * @date 2020/05/14
 */
@Service
public class PcbdetailServiceImpl implements PcbdetailService {

    @Autowired
    private PcbdetailRepository pcbdetailRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public Pcbdetail getById(Long id) {
        return pcbdetailRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<Pcbdetail> getPageList(Example<Pcbdetail> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return pcbdetailRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param pcbdetail 实体对象
     */
    @Override
    public Pcbdetail save(Pcbdetail pcbdetail) {
        return pcbdetailRepository.save(pcbdetail);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return pcbdetailRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}