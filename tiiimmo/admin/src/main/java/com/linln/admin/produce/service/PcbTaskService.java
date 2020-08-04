package com.linln.admin.produce.service;

import com.linln.RespAndReqs.PcbTaskReq;
import com.linln.RespAndReqs.ProcessTaskReq;
import com.linln.RespAndReqs.responce.PlateNoInfo;
import com.linln.admin.produce.domain.PcbTask;
import com.linln.admin.produce.domain.ProcessTask;
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


    ResultVo getFeedingTaskFromERP(String FRWFBillNo) ;
    ResultVo putIntoProduceBefore(Long pcbTaskId);

    ResultVo putIntoProduceByPlateInfo(PlateNoInfo plateNoInfo);

    ResultVo putIntoProduce(PcbTaskReq pcbTaskReq);

    ResultVo findProcessTaskByPCBTaskId(Long id);

    ResultVo findProcessTaskByProcessName(PcbTaskReq pcbTaskReq);

    ResultVo findFeedingTask(PcbTaskReq pcbTaskReq);

    Map<String,Object> deviceProduceAmount(PcbTaskReq pcbTaskReq);


    Map<String,Object> checkPositionSort(PcbTaskReq pcbTaskReq);


    Map<String,Object> noticePutinStatus(PcbTaskReq pcbTaskReq);


    Map<String,Object> scanCountPlate(PcbTaskReq pcbTaskReq);

    ResultVo findProcessTaskByDevice(PcbTaskReq pcbTaskReq);

    ResultVo modifyProcessTaskStatus(PcbTaskReq pcbTaskReq);

     ResultVo modifyProcessTaskStatus2(PcbTaskReq pcbTaskReq);

    ResultVo settlementDetailCount(PcbTaskReq pcbTaskReq);

    ResultVo countProcessTaskAmount(PcbTaskReq pcbTaskReq);

    ResultVo tempChangeTaskrocess(PcbTaskReq pcbTaskReq);

    ResultVo findScheduling(PcbTaskReq req);

    ResultVo recordBadTypeList(PcbTaskReq req);

    ResultVo findBadTypeRecordList(PcbTaskReq req);

    /**
     * 更新排产工序
     * @param processTaskReq
     * @return
     */
    ResultVo updateProcessTask(ProcessTaskReq processTaskReq);

    ResultVo updateProcess(ProcessTask processTask);

    ResultVo findPcbTaskPlateNo(PcbTaskReq req);

    ResultVo deviceAmountAndworkTime(PcbTaskReq req);

    ResultVo generateBatchId(Long pcbTaskId);
}