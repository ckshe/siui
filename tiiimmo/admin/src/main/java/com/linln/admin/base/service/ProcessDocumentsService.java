package com.linln.admin.base.service;

import com.linln.admin.base.domain.ProcessDocuments;
import com.linln.common.enums.StatusEnum;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author www
 * @date 2020/09/14
 */
public interface ProcessDocumentsService {

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<ProcessDocuments> getPageList(Example<ProcessDocuments> example);

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    ProcessDocuments getById(Long id);

    /**
     * 保存数据
     * @param processDocuments 实体对象
     */
    ProcessDocuments save(ProcessDocuments processDocuments);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Transactional
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);

    //根据规格型号查询

    ProcessDocuments findByPcbCode(String pcbCode);

    void showPDF(HttpServletResponse response, Long id);
    void showPDFByPcbCode(HttpServletResponse response, String pcbCode);
}