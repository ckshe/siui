package com.linln.admin.reports.service;

import com.linln.admin.base.domain.Device;
import com.linln.admin.produce.domain.PcbTask;
import com.linln.admin.produce.domain.ProcessTask;

import java.util.List;
import java.util.Map;

public interface ShowBoardService {

    public List<PcbTask> pcbTaskBoard();

    public Map<String,Object> getPcbTaskThisWeek();

    public Map<String,Object> getProcessTaskThisWeek();

    public List<Map<String, Object>> getTaskFinishRate();


    public List<Device> getDeviceStatus();

    public  List<Map<String,Object>> staffOnBoard();

    public ProcessTask findByProcessTaskCode(String processTaskCode);
}
