package com.linln.admin.produce.service;

import com.linln.RespAndReqs.PcbTaskPositionRecordDetailReq;
import com.linln.RespAndReqs.PcbTaskReq;
import com.linln.admin.produce.domain.PcbTaskPositionRecordDetail;
import com.linln.common.vo.ResultVo;

import java.util.List;

public interface PcbTaskPositionRecordDetailService {
    PcbTaskPositionRecordDetail downPositionRecordDetail(PcbTaskReq req);

    ResultVo findFinishRecordDetail(PcbTaskPositionRecordDetailReq req);
}
