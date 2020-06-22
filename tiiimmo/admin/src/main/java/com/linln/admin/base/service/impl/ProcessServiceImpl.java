package com.linln.admin.base.service.impl;

import com.linln.admin.base.domain.Process;
import com.linln.admin.base.repository.ProcessRepository;
import com.linln.admin.base.service.ProcessService;
import com.linln.common.data.PageSort;
import com.linln.common.enums.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author www
 * @date 2020/05/13
 */
@Service
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    private ProcessRepository processRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public Process getById(Long id) {
        return processRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<Process> getPageList(Example<Process> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        Sort sort = new Sort(Sort.Direction.ASC,"\\Qsort_no\\E");
        PageRequest pageRequest = new PageRequest(page.getPageNumber(),page.getPageSize(),sort);
        return processRepository.findAll(example, pageRequest);
    }

    /**
     * 保存数据
     * @param process 实体对象
     */
    @Override
    public Process save(Process process) {
        return processRepository.save(process);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return processRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }

    /**
     * 列表数据下移
     * @param id
     */
    @Override
    public void moveDown(Long id) {
        //获取当前的数据信息（即准备下移的数据）
        Process process = processRepository.findByPId(id);
        //查询下一条的数据信息
        //Process processNext = processRepository.findByPId(id+1);
        Process processNext = processRepository.moveDown(process.getSort_no());
        //最下面的数据信息不能下移
        if (processNext == null) {
            return;
        }
        //交换两条数据信息的sort_no值
        Integer temp = process.getSort_no();
        process.setSort_no(processNext.getSort_no());
        processNext.setSort_no(temp);
        //更新到数据库
        processRepository.save(process);
        processRepository.save(processNext);
    }

    /**
     * 列表数据上移
     * @param id
     */
    @Override
    public void moveUp(Long id) {
        //获取当前的数据信息（即准备下移的数据）
        Process process = processRepository.findByPId(id);
        //查询上一条的数据信息
        Process processBefore = processRepository.moveUp(process.getSort_no());
        //最上面的数据不能上移
        if (processBefore == null){
            return;
        }
        //交换两条数据信息的sort_no值
        Integer temp = process.getSort_no();
        process.setSort_no(processBefore.getSort_no());
        processBefore.setSort_no(temp);
        //更新到数据库
        processRepository.save(process);
        processRepository.save(processBefore);

    }

    @Override
    public List<Process> list() {
        return processRepository.findAll();
    }

    @Override
    public List<String> queryProcessType() {
        return processRepository.queryProcessType();
    }
}