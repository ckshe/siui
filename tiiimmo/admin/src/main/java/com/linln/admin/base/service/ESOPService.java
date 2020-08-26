package com.linln.admin.base.service;

import com.linln.admin.base.domain.ESOP;
import com.linln.admin.base.domain.OperationInstruction;
import com.linln.admin.base.util.ApiResponse;
import com.linln.admin.produce.domain.FileRecord;
import com.linln.common.enums.StatusEnum;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 连
 * @date 2020/06/09
 */
public interface ESOPService {

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<ESOP> getPageList(Example<ESOP> example);

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    ESOP getById(Long id);

    /**
     * 保存数据
     * @param
     */
    ESOP save(ESOP esop);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Transactional
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);

    /**
     * 导入操作手册
     * @param file
     * @return
     */
    ApiResponse importOperationManual(MultipartFile file);
    //查看pdf
    void showPDF(HttpServletResponse response, Long id);

    FileRecord saveFileRecord(FileRecord fileRecord);

}