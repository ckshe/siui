package com.linln.admin.base.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.StatusUtil;
import com.linln.modules.system.domain.User;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * @author ww
 * @date 2020/06/17
 */
@Data
@Entity
@Table(name="base_device_product_element")
@EntityListeners(AuditingEntityListener.class)
@Where(clause = StatusUtil.NOT_DELETE)
public class DeviceProductElement implements Serializable {
    // 主键ID
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    // 图样名
    private String sample_name;
    // 跳过状态
    private String skip_status;
    // 机台编号
    private String device_code;
    // 元件号码
    private String element_no;
    // 元件名
    private String element_name;
    // 物料编号
    //@Column(name = "productCode")
    private String product_code;
    // 安装位置
    private String position;
    // 创建时间
    @CreatedDate
    private Date createDate;
    // 更新时间
    @LastModifiedDate
    private Date updateDate;
    // 规格型号
    private String pcb_code;
    //AB面
    private String a_or_b;

    private String X ;

    private String Y ;

    private String R ;
    //工作台
    private String work_desk ;
    //Head
    private String head ;
    //坏板标记
    private String bad_flag ;
    //基准标记
    private String base_flag ;
    //轨道
    private String track ;
    //原拼板号码
    private String ori_palte_no ;
    //送料器类型
    private String machine_type;
    //组ID
    private String group_id ;
    //组内顺序
    private String group_sort ;
  /*  //备注
    private String remark;*/


    // 创建者
    @CreatedBy
    @ManyToOne(fetch=FetchType.LAZY)
    @NotFound(action=NotFoundAction.IGNORE)
    @JoinColumn(name="create_by")
    @JsonIgnore
    private User createBy;
    // 更新者
    @LastModifiedBy
    @ManyToOne(fetch=FetchType.LAZY)
    @NotFound(action=NotFoundAction.IGNORE)
    @JoinColumn(name="update_by")
    @JsonIgnore
    private User updateBy;
    // 数据状态
    private Byte status = StatusEnum.OK.getCode();

}