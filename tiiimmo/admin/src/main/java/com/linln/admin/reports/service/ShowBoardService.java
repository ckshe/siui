package com.linln.admin.reports.service;

import com.linln.RespAndReqs.PcbTaskReq;
import com.linln.RespAndReqs.responce.BadRateResp;
import com.linln.RespAndReqs.responce.DeviceRunTimeResp;
import com.linln.RespAndReqs.responce.ProcessThisWeekRateResp;
import com.linln.RespAndReqs.responce.StaffOntimeRateResp;
import com.linln.admin.base.domain.Device;
import com.linln.admin.produce.domain.PcbTask;
import com.linln.admin.produce.domain.ProcessTask;
import com.linln.admin.produce.domain.UserDeviceHistory;
import com.linln.common.vo.ResultVo;
import com.linln.modules.system.domain.User;

import java.util.List;
import java.util.Map;

public interface ShowBoardService {

    public List<PcbTask> pcbTaskBoard();

    public Map<String,Object> getMapWeekRate();

    public Map<String,Object> getMapProcessWeekRate();

    public List<Map<String, Object>> getTaskFinishRate();


    public List<Device> getDeviceStatus();

    public List<Map<String,Object>> staffOnBoard();

    public ProcessTask findByProcessTaskCode(String processTaskCode);

    public List<ProcessTask> findProcessTaskByDate();

    public List<ProcessThisWeekRateResp> getMapProcessThisWeekRate();

    public Map<String,Object> getMapProcessDayRate();

    public  Map<String,Object> getMapProcessTypeDayRate();

    public List<StaffOntimeRateResp>  staffTodayOntimeRate();

    ResultVo findProcessTaskByDevice(PcbTaskReq pcbTaskReq);

    public List<BadRateResp> processBadRate();

    public Device getDeviceByCode(String deviceCode);

    public  List<DeviceRunTimeResp> getDeviceRunTime(String deviceCode);

    public Map<String,Map<String,Object>> findByStartEndTimeBy3TiePian();

    public User findOneLastOnTimeUser(String deviceCode);


}
