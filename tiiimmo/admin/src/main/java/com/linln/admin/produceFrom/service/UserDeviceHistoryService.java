package com.linln.admin.produceFrom.service;

import com.linln.RespAndReqs.UserDeviceHistoryReq;
import com.linln.common.vo.ResultVo;

public interface UserDeviceHistoryService {
    ResultVo findDeviceHistory(UserDeviceHistoryReq req);
}
