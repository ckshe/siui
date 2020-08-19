package com.linln.admin.produce.service;

import com.linln.RespAndReqs.PcbTaskPositionRecordDetailReq;
import com.linln.RespAndReqs.PcbTaskReq;
import com.linln.admin.produce.domain.PcbTaskPositionRecordDetail;
import com.linln.common.vo.ResultVo;

import java.util.List;

public interface PcbTaskPositionRecordDetailService {
    PcbTaskPositionRecordDetail downPositionRecordDetail(PcbTaskReq req);

    /**
     * 查询已上料列表
     * @param req 查询对象
     * @return 上料列表
     */
    ResultVo findFinishRecordDetail(PcbTaskPositionRecordDetailReq req);
}
