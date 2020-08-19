package com.linln.admin.produce.service;

import com.linln.admin.produce.domain.CraftParameterRecord;
import com.linln.admin.produce.request.CraftParameterRecordReq;
import com.linln.common.vo.ResultVo;

public interface CraftParameterRecordService {
    public void addCraftParameterRecord(CraftParameterRecord record);

    public ResultVo findDeviceCraftParamByDeviceCode(String deviceCode);

    public ResultVo findCraftParameterRecordByProcessTaskCode(String processTaskCode);

    public ResultVo qcCraftParameterRecord(CraftParameterRecordReq recordReq);
}
