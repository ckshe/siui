package com.linln.admin.maintenance.service.impl;

import com.linln.admin.maintenance.domain.SpotCheck;
import com.linln.admin.maintenance.repository.SpotCheckRepository;
import com.linln.admin.maintenance.service.SpotCheckService;
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
 * @author 小懒虫
 * @date 2020/08/19
 */
@Service
public class SpotCheckServiceImpl implements SpotCheckService {

    @Autowired
    private SpotCheckRepository spotCheckRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public SpotCheck getById(Long id) {
        return spotCheckRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<SpotCheck> getPageList(Example<SpotCheck> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return spotCheckRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param spotCheck 实体对象
     */
    @Override
    public SpotCheck save(SpotCheck spotCheck) {
        return spotCheckRepository.save(spotCheck);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return spotCheckRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}