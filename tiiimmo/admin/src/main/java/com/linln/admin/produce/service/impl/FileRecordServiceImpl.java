package com.linln.admin.produce.service.impl;

import com.linln.admin.produce.domain.FileRecord;
import com.linln.admin.produce.repository.FileRecordRepository;
import com.linln.admin.produce.service.FileRecordService;
import com.linln.common.data.PageSort;
import com.linln.common.enums.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author www
 * @date 2020/08/25
 */
@Service
public class FileRecordServiceImpl implements FileRecordService {

    @Autowired
    private FileRecordRepository fileRecordRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public FileRecord getById(Long id) {
        return fileRecordRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<FileRecord> getPageList(Example<FileRecord> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return fileRecordRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param fileRecord 实体对象
     */
    @Override
    public FileRecord save(FileRecord fileRecord) {
        return fileRecordRepository.save(fileRecord);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return fileRecordRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}