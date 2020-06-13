package com.linln.admin.base.service.impl;

import com.linln.admin.base.domain.Line;
import com.linln.admin.base.repository.LineRepository;
import com.linln.admin.base.service.LineService;
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
 * @author ww
 * @date 2020/05/14
 */
@Service
public class LineServiceImpl implements LineService {

    @Autowired
    private LineRepository lineRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public Line getById(Long id) {
        return lineRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<Line> getPageList(Example<Line> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        Page<Line> lines = lineRepository.findAll(example, page);
        return lines;
    }

    /**
     * 保存数据
     * @param line 实体对象
     */
    @Override
    public Line save(Line line) {
        return lineRepository.save(line);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return lineRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}