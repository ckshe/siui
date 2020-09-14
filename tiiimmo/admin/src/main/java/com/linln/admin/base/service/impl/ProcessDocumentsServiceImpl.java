package com.linln.admin.base.service.impl;

import com.linln.admin.base.domain.ProcessDocuments;
import com.linln.admin.base.repository.ProcessDocumentsRepository;
import com.linln.admin.base.service.ProcessDocumentsService;
import com.linln.common.data.PageSort;
import com.linln.common.enums.StatusEnum;
import com.linln.constant.CommonConstant;
import com.linln.utill.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author www
 * @date 2020/09/14
 */
@Service
public class ProcessDocumentsServiceImpl implements ProcessDocumentsService {

    @Autowired
    private ProcessDocumentsRepository processDocumentsRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public ProcessDocuments getById(Long id) {
        return processDocumentsRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<ProcessDocuments> getPageList(Example<ProcessDocuments> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return processDocumentsRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param processDocuments 实体对象
     */
    @Override
    public ProcessDocuments save(ProcessDocuments processDocuments) {
        return processDocumentsRepository.save(processDocuments);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return processDocumentsRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }

    @Override
    public ProcessDocuments findByPcbCode(String pcbCode) {
        return processDocumentsRepository.findAllByPcbCode(pcbCode);
    }

    @Override
    public void showPDF(HttpServletResponse response, Long id) {
        ProcessDocuments processDocuments = processDocumentsRepository.findById(id).get();
        if(processDocuments==null){
            return;
        }
        String path = CommonConstant.file_path+CommonConstant.process_document_path+processDocuments.getUploadFile();
        FileUtil.readPDF(response,path);

    }

    @Override
    public void showPDFByPcbCode(HttpServletResponse response, String pcbCode) {
        ProcessDocuments processDocuments = processDocumentsRepository.findAllByPcbCode(pcbCode);
        if(processDocuments==null){
            return;
        }
        String path = CommonConstant.file_path+CommonConstant.process_document_path+processDocuments.getUploadFile();
        FileUtil.readPDF(response,path);
    }
}