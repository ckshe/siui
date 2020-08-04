package com.linln.admin.base.repository;

import com.linln.admin.base.domain.ElementProduct;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

public interface ElementProductRepository extends BaseRepository<ElementProduct,Long> {

    @Query(value = "select * from base_element_product where element_name = ?1",nativeQuery = true)
    ElementProduct findByElement_name(String elementCode);
}
