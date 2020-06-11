package com.linln.admin.base.service;

import com.linln.admin.base.domain.OperationInstruction;
import com.linln.admin.base.util.ApiResponse;
import com.linln.common.enums.StatusEnum;
import com.linln.common.vo.ResultVo;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 连
 * @date 2020/06/09
 */
public interface OperationInstructionService {

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<OperationInstruction> getPageList(Example<OperationInstruction> example);

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    OperationInstruction getById(Long id);

    /**
     * 保存数据
     * @param operationInstruction 实体对象
     */
    OperationInstruction save(OperationInstruction operationInstruction);

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
}