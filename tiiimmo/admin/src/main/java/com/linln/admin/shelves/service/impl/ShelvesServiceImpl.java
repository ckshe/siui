package com.linln.admin.shelves.service.impl;

import com.linln.admin.shelves.domain.Shelves;
import com.linln.admin.shelves.repository.ShelvesRepository;
import com.linln.admin.shelves.service.ShelvesService;
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
public class ShelvesServiceImpl implements ShelvesService {

    @Autowired
    private ShelvesRepository shelvesRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public Shelves getById(Long id) {
        return shelvesRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<Shelves> getPageList(Example<Shelves> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return shelvesRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param shelves 实体对象
     */
    @Override
    public Shelves save(Shelves shelves) {
        return shelvesRepository.save(shelves);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return shelvesRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}