package com.linln.admin.badNews.service.impl;

import com.linln.admin.badNews.domain.BadNews;
import com.linln.admin.badNews.repository.BadNewsRepository;
import com.linln.admin.badNews.service.BadNewsService;
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
public class BadNewsServiceImpl implements BadNewsService {

    @Autowired
    private BadNewsRepository badNewsRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public BadNews getById(Long id) {
        return badNewsRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<BadNews> getPageList(Example<BadNews> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return badNewsRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param badNews 实体对象
     */
    @Override
    public BadNews save(BadNews badNews) {
        return badNewsRepository.save(badNews);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return badNewsRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}