package com.linln.admin.maintenance.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.StatusUtil;
import com.linln.modules.system.domain.User;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
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
 * @author 小懒虫
 * @date 2020/08/19
 */
@Data
@Entity
@Table(name="produce_spot_check")
@EntityListeners(AuditingEntityListener.class)
@Where(clause = StatusUtil.NOT_DELETE)
public class SpotCheck implements Serializable {
    // 主键ID
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    // 点检日期
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date checkData;
    // 点检设备
    private String checkDevice;
    // 点检人
    private String checkUser;
    // 点检内容
    private String checkContent;
    // 点检结果
    private String checkResult;
    // 备注
    private String remark;
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