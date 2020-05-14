package com.linln.admin.classInfo.service.impl;

import com.linln.admin.classInfo.domain.ClassInfo;
import com.linln.admin.classInfo.repository.ClassInfoRepository;
import com.linln.admin.classInfo.service.ClassInfoService;
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
 * @date 2020/05/13
 */
@Service
public class ClassInfoServiceImpl implements ClassInfoService {

    @Autowired
    private ClassInfoRepository classInfoRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public ClassInfo getById(Long id) {
        return classInfoRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<ClassInfo> getPageList(Example<ClassInfo> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return classInfoRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param classInfo 实体对象
     */
    @Override
    public ClassInfo save(ClassInfo classInfo) {
        return classInfoRepository.save(classInfo);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return classInfoRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}