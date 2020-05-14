package com.linln.admin.product.service;

import com.linln.admin.product.domain.Product;
import com.linln.common.enums.StatusEnum;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author www
 * @date 2020/05/13
 */
public interface ProductService {

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<Product> getPageList(Example<Product> example);

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    Product getById(Long id);

    /**
     * 保存数据
     * @param product 实体对象
     */
    Product save(Product product);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Transactional
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);
}