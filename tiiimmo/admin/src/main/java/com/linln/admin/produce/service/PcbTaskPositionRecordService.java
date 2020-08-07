package com.linln.admin.produce.service;

import com.linln.RespAndReqs.PcbTaskReq;
import com.linln.admin.produce.domain.PcbTaskPositionRecord;
import com.linln.admin.produce.domain.PcbTaskPositionRecordDetail;
import com.linln.common.vo.ResultVo;

import java.util.List;

public interface PcbTaskPositionRecordService {

    public PcbTaskPositionRecord buildPositionRecordAndReturn(PcbTaskReq req);

    public List<PcbTaskPositionRecordDetail> getPositionRecord(PcbTaskReq req);

    public ResultVo startPositonRecord(PcbTaskReq req);
    public ResultVo scanProductCode(PcbTaskReq req);

    public ResultVo confirmScanProductCode(PcbTaskReq pcbTaskReq);

    public ResultVo finishPositionRecord(PcbTaskReq req);

    public ResultVo getUserInfoByCard(String cardSequence);

    ResultVo getDetailByProductCode(String productCode);

}
