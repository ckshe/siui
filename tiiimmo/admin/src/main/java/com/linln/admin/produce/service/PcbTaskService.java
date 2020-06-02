package com.linln.admin.produce.service;

import com.linln.RespAndReqs.PcbTaskReq;
import com.linln.admin.produce.domain.PcbTask;
import com.linln.common.enums.StatusEnum;
import com.linln.common.vo.ResultVo;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author 小懒虫
 * @date 2020/05/18
 */
public interface PcbTaskService {

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<PcbTask> getPageList(Example<PcbTask> example);

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    PcbTask getById(Long id);

    /**
     * 保存数据
     * @param pcbTask 实体对象
     */
    PcbTask save(PcbTask pcbTask);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Transactional
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);

    ResultVo getPcbTaskFromERP(String dataBetween) ;


    ResultVo getFeedingTaskFromERP(String dataBetween) ;
    ResultVo putIntoProduceBefore(Long pcbTaskId);

    ResultVo putIntoProduce(PcbTaskReq pcbTaskReq);

    ResultVo findProcessTaskByPCBTaskId(Long id);

    ResultVo findProcessTaskByProcessName(PcbTaskReq pcbTaskReq);

    ResultVo findFeedingTask(PcbTaskReq pcbTaskReq);

    Map<String,Object> deviceProduceAmount(PcbTaskReq pcbTaskReq);

    ResultVo findProcessTaskByDevice(PcbTaskReq pcbTaskReq);

}