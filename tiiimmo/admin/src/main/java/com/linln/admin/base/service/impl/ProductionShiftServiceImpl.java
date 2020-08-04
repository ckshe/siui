package com.linln.admin.base.service.impl;

import com.linln.admin.base.domain.ProductionShift;
import com.linln.admin.base.repository.ProductionShiftRepository;
import com.linln.admin.base.service.ProductionShiftService;
import com.linln.common.data.PageSort;
import com.linln.common.enums.StatusEnum;
import com.linln.modules.system.domain.User;
import com.linln.modules.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 风陵苑主
 * @date 2020/06/12
 */
@Service
public class ProductionShiftServiceImpl implements ProductionShiftService {

    @Autowired
    private ProductionShiftRepository productionShiftRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public ProductionShift getById(Long id) {
        return productionShiftRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<ProductionShift> getPageList(Example<ProductionShift> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return productionShiftRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param productionShift 实体对象
     */
    @Override
    public ProductionShift save(ProductionShift productionShift) {
        String userName = productionShift.getUserName();
        User users = userRepository.queryByUserName(userName);
        productionShift.setUsers(users);
        //productionShift.setStation(users.getRoleNames());
        String role = users.getRoles().iterator().next().getTitle();
        productionShift.setStation(role);

        return productionShiftRepository.save(productionShift);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return productionShiftRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }

    @Override
    public void delete(Long id) {
        productionShiftRepository.deleteById(id);
    }
}