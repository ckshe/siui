package com.linln.admin.base.service.impl;

import com.linln.admin.base.domain.LightPlate;
import com.linln.admin.base.repository.LightPlateRepository;
import com.linln.admin.base.service.LightPlateService;
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
 * @date 2020/06/09
 */
@Service
public class LightPlateServiceImpl implements LightPlateService {

    @Autowired
    private LightPlateRepository lightPlateRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public LightPlate getById(Long id) {
        return lightPlateRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<LightPlate> getPageList(Example<LightPlate> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return lightPlateRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param lightPlate 实体对象
     */
    @Override
    public LightPlate save(LightPlate lightPlate) {
        return lightPlateRepository.save(lightPlate);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return lightPlateRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}