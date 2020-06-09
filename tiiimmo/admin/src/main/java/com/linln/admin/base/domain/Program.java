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
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author 连
 * @date 2020/06/09
 */
@Data
@Entity
@Table(name="base_program")
@EntityListeners(AuditingEntityListener.class)
@Where(clause = StatusUtil.NOT_DELETE)
/**
 * 程序
 */
public class Program implements Serializable {
    // 主键ID
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    // 设备编号
    private String code;
    private String name;
    // PCB光板号
    private String pcbCode;
    // AB面
    private String face;
    // 成品号
    private String productNo;
    // 程序名称
    private String processName;
    // 修改日期
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date modifyDate;
    // 程序文件
    private String processFile;
    // 备注
    private String remark;
    // 创建时间
    @CreatedDate
    private Date createDate;
    // 更新时间
    @LastModifiedDate
    private Date updateDate;
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