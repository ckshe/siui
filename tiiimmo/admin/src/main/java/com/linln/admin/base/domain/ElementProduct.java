package com.linln.admin.base.domain;

import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.StatusUtil;
import lombok.Data;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@Entity
@Table(name="base_element_product")
public class ElementProduct {
    // 主键ID
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    //物料编号
    private String product_code;

    //元件编号
    private String element_name;

    // 数据状态
    private Byte status = StatusEnum.OK.getCode();
}
