package com.linln.admin.base.repository;

import com.linln.admin.base.domain.ProductionShift;
import com.linln.modules.system.domain.User;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author 风陵苑主
 * @date 2020/06/12
 */
public interface ProductionShiftRepository extends BaseRepository<ProductionShift, Long> {
}