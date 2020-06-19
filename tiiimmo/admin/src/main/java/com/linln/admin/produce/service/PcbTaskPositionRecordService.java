package com.linln.admin.produce.service;

import com.linln.RespAndReqs.PcbTaskReq;
import com.linln.admin.produce.domain.PcbTaskPositionRecordDetail;

import java.util.List;

public interface PcbTaskPositionRecordService {

    public void buildPositionRecord(PcbTaskReq req);

    public List<PcbTaskPositionRecordDetail> getPositionRecord(PcbTaskReq req);

}
