package com.linln.admin.fixture.service.impl;

import com.linln.admin.fixture.domain.EditFixture;
import com.linln.admin.fixture.repository.EditFixtureRepository;
import com.linln.admin.fixture.service.EditFixtureService;
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
public class EditFixtureServiceImpl implements EditFixtureService {

    @Autowired
    private EditFixtureRepository editFixtureRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public EditFixture getById(Long id) {
        return editFixtureRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<EditFixture> getPageList(Example<EditFixture> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return editFixtureRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param editFixture 实体对象
     */
    @Override
    public EditFixture save(EditFixture editFixture) {
        return editFixtureRepository.save(editFixture);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return editFixtureRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}