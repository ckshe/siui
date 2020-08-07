package com.linln.admin.base.repository;

import com.linln.admin.base.domain.DeviceProductReplaceElement;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author ww
 * @date 2020/08/01
 */
public interface DeviceProductReplaceElementRepository extends BaseRepository<DeviceProductReplaceElement, Long> {

    @Query(value = "select * from base_device_product_replace_element where  original_product_code = ?1",nativeQuery = true)
    List<DeviceProductReplaceElement> findByOriginal_product_code(String originalProductCode);

    @Query(value = "select * from base_device_product_replace_element where  replace_product_code = ?1",nativeQuery = true)
    List<DeviceProductReplaceElement> findByReplace_prodcut_code(String replaceProductCode);
    //________________________
    @Query(value = "select * from base_device_product_replace_element where  original_product_code = ?1",nativeQuery = true)
    DeviceProductReplaceElement queryByOriginalProductCode(String original_product_code);

    @Query(value = "select * from base_device_product_replace_element where  replace_product_code = ?1",nativeQuery = true)
    DeviceProductReplaceElement findByReplaceProductCode(String replaceProductCode);

}