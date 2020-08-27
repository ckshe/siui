package com.linln.admin.produce.service;

import com.linln.admin.produce.domain.FileRecord;
import com.linln.common.enums.StatusEnum;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author www
 * @date 2020/08/25
 */
public interface FileRecordService {

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<FileRecord> getPageList(Example<FileRecord> example);

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    FileRecord getById(Long id);

    /**
     * 保存数据
     * @param fileRecord 实体对象
     */
    FileRecord save(FileRecord fileRecord);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Transactional
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);

    //查看pdf
    void showPDF(HttpServletResponse response, Long id);
}