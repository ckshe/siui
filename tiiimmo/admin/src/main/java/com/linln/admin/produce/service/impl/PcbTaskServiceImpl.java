package com.linln.admin.produce.service.impl;

import com.linln.admin.base.repository.ModelsRepository;
import com.linln.admin.produce.domain.PcbTask;
import com.linln.admin.produce.repository.PcbTaskRepository;
import com.linln.admin.produce.service.PcbTaskService;
import com.linln.common.data.PageSort;
import com.linln.common.enums.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 小懒虫
 * @date 2020/05/18
 */
@Service
public class PcbTaskServiceImpl implements PcbTaskService {

    @Autowired
    private PcbTaskRepository pcbTaskRepository;

    @Autowired
    private ModelsRepository modelsRepository;


    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public PcbTask getById(Long id) {
        return pcbTaskRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<PcbTask> getPageList(Example<PcbTask> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return pcbTaskRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param pcbTask 实体对象
     */
    @Override
    public PcbTask save(PcbTask pcbTask) {
        return pcbTaskRepository.save(pcbTask);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return pcbTaskRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }

    @Override
    public void getDataFromERP(String dataBetween) {

        List<Map<String,Object>> mapList = new ArrayList<>();
        for (Map<String,Object> map:mapList){
            // 制造编号
            String task_sheet_id;
            // 厂区
            String factory;
            // 车间
            String workshop;
            // 机型编码
            String model_id;
            // 机型名称
            String model_name;
            // 机型版本
            String model_ver;
            // RoHS
            String is_rohs;
            // 生产数量
            Integer quantity;
            // 计划完成时间
            Date plan_complete_date;
            // 通知日期
            Date task_sheet_date;
            // PCB编码
            String pcb_id;
            // PCB名称
            String pcb_name;


            // PCB数量需要自己算工程
            Integer pcb_quantity;
            // 任务号需自己生成
            String pcb_task_id;


           // 生成任务单同时根据编码规则生成流通表

        }


    }
}