package com.linln.admin.produce.domain;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Table(name = "produce_feeding_task")
@Entity

public class FeedingTask implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //物料编码
    private String product_code;
    //物料名称
    private String product_name;
    //规格型号
    private String specification_model;
    //发料仓库
    private String stock_name;
    //单位
    private String unit;
    //单位用量
    private BigDecimal FQtyScrap;
    //计划投料数
    private BigDecimal FQtyMust;
    //已领数量
    private BigDecimal FStockQty;
    //未领数量
    private BigDecimal FNStockQty;
    //库存
    private BigDecimal FQty;
    //齐套率
    private String FQtlv;
    //生产任务单号
    private String pcb_task_code;
    //投料单号
    private String feeding_task_code;

    //shi否冻结
    private Byte status;






}
