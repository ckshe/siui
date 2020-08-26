package com.linln.admin.base.service.impl;

import com.linln.admin.base.domain.Process;
import com.linln.admin.base.repository.ProcessRepository;
import com.linln.admin.base.service.ProcessService;
import com.linln.common.data.PageSort;
import com.linln.common.enums.StatusEnum;
import com.linln.common.exception.ResultException;
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
        //获取当前最大的sort值
        Integer maxSortNo = processRepository.getMaxSortNo();
        if (maxSortNo == 0){
            process.setSort_no(1);
        } else {
            process.setSort_no(maxSortNo + 1);
        }
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
            throw new ResultException("最后一条数据不能再往下移了哦");
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
            throw new ResultException("第一条数据不能再往上移了哦");
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

    /**
     * 查询工序类型
     * @return 工序类型集合
     */
    @Override
    public List<String> queryProcessType() {
        return processRepository.queryProcessType();
    }

    /**
     * 查询工序名称
     * @return 工序名称集合
     */
    @Override
    public List<String> queryProcessName() {
        return processRepository.queryProcessName();
    }
}