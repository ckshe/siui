package com.linln.admin.reports.service;

import com.linln.admin.produce.domain.PcbTask;

import java.util.List;
import java.util.Map;

public interface ShowBoardService {

    public List<PcbTask> pcbTaskBoard();

    public Map<String,Object> getProcessTaskThisWeekProcess();
}
