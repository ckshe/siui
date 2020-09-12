package com.linln.admin.produce.service.impl;

import com.linln.admin.base.domain.Pcb;
import com.linln.admin.produce.domain.CurrentReport;
import com.linln.admin.produce.domain.PcbTask;
import com.linln.admin.produce.repository.CurrentReportRepository;
import com.linln.admin.produce.repository.PcbTaskRepository;
import com.linln.admin.produce.service.CurrentReportService;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import org.apache.xmlbeans.impl.common.ResolverUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrentReportServiceImpl implements CurrentReportService {

    @Autowired
    private CurrentReportRepository currentReportRepository;
    @Autowired
    private PcbTaskRepository pcbTaskRepository;

    @Override
    public ResultVo addCurrentReport(CurrentReport currentReport) {

        if(currentReport.getPcb_task_code()==null){
            return ResultVoUtil.success("排产计划号不能为空");
        }
        List<PcbTask> allByPcb_task_code = pcbTaskRepository.findAllByPcb_task_code(currentReport.getPcb_task_code());
        if(allByPcb_task_code.size()==0){
            return ResultVoUtil.success("找不到该排产计划");
        }
        PcbTask pcbTask = allByPcb_task_code.get(0);
        CurrentReport oldCurrentReport = currentReportRepository.findByPcb_task_code(currentReport.getPcb_task_code(),currentReport.getReport_type());
        if(oldCurrentReport!=null){
           oldCurrentReport.setJson_data(currentReport.getJson_data());
           currentReportRepository.save(oldCurrentReport);

        }else {
            currentReport.setPcb_code(pcbTask.getPcb_id());
            currentReport.setTask_sheet_code(pcbTask.getTask_sheet_code());
            currentReportRepository.save(currentReport);

        }
        return ResultVoUtil.success("保存成功");
    }

    @Override
    public ResultVo addDeviceRepairReport(CurrentReport currentReport) {
        currentReportRepository.save(currentReport);
        return ResultVoUtil.success("保存成功");    }
}
