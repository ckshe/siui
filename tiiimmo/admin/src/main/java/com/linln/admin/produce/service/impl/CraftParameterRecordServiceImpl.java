package com.linln.admin.produce.service.impl;

import com.linln.admin.base.domain.Device;
import com.linln.admin.base.domain.DeviceCraftParameter;
import com.linln.admin.base.domain.ProductionShift;
import com.linln.admin.base.repository.DeviceCraftParameterRepository;
import com.linln.admin.base.repository.DeviceRepository;
import com.linln.admin.base.repository.ProductionShiftRepository;
import com.linln.admin.produce.domain.CraftParameterRecord;
import com.linln.admin.produce.domain.ProcessTask;
import com.linln.admin.produce.domain.UserDeviceHistory;
import com.linln.admin.produce.repository.CraftParameterRecordRepository;
import com.linln.admin.produce.repository.ProcessTaskRepository;
import com.linln.admin.produce.repository.UserDeviceHistoryRepository;
import com.linln.admin.produce.request.CraftParameterRecordReq;
import com.linln.admin.produce.service.CraftParameterRecordService;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import com.linln.modules.system.domain.User;
import com.linln.modules.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CraftParameterRecordServiceImpl implements CraftParameterRecordService {

    @Autowired
    private CraftParameterRecordRepository craftParameterRecordRepository;

    @Autowired
    private ProcessTaskRepository processTaskRepository;

    @Autowired
    private ProductionShiftRepository productionShiftRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDeviceHistoryRepository userDeviceHistoryRepository;

    @Autowired
    private DeviceCraftParameterRepository deviceCraftParameterRepository;
    @Override
    public void addCraftParameterRecord(CraftParameterRecord record) {

        Date today = new Date();
        //班次信息根据上机记录用户查找
        List<UserDeviceHistory> historyList = userDeviceHistoryRepository.findOneLastOnTime(record.getDevice_code());
        User user = userRepository.queryByUserName(historyList.get(0).getUser_name());
        List<ProductionShift> productionShift = productionShiftRepository.findByUserid(user.getId());
        record.setClass_info(productionShift.get(0).getShift());
        record.setRecord_name(user.getNickname());
        //数量根据工序计划查找
        ProcessTask processTask = processTaskRepository.findByProcessTaskCode(record.getProcess_task_code());
        record.setPcb_task_code(processTask.getPcb_task_code());
        //设备名称根据设备号查找
        Device device = deviceRepository.findbyDeviceCode(record.getDevice_code());
        record.setDevice_name(device.getDevice_name());
        record.setRecord_time(today);
        craftParameterRecordRepository.save(record);
    }

    @Override
    public ResultVo findDeviceCraftParamByDeviceCode(String deviceCode) {
        List<DeviceCraftParameter> paramList = deviceCraftParameterRepository.findByDevice_code(deviceCode);

        return ResultVoUtil.success(paramList);
    }


    @Override
    public ResultVo findCraftParameterRecordByProcessTaskCode(CraftParameterRecordReq recordReq) {
        ProcessTask processTask = processTaskRepository.findByProcessTaskCode(recordReq.getProcessTaskCode());
        List<CraftParameterRecord> recordlist = craftParameterRecordRepository.findByProcess_task_code(recordReq.getProcessTaskCode());
        if(recordlist==null||recordlist.size()==0){
            List<DeviceCraftParameter> paramList = deviceCraftParameterRepository.findByDevice_code(recordReq.getDeviceCode());
            String deviceParam = "|";
            for(DeviceCraftParameter parameter : paramList){
                deviceParam = deviceParam+parameter.getCraft()+": 0"+parameter.getParameter()+"|";
            }

            Date today = new Date();
            CraftParameterRecord record = new CraftParameterRecord();
            //班次信息根据上机记录用户查找
            List<UserDeviceHistory> historyList = userDeviceHistoryRepository.findOneLastOnTime(recordReq.getDeviceCode());
            User user = userRepository.queryByUserName(historyList.get(0).getUser_name());
            List<ProductionShift> productionShift = productionShiftRepository.findByUserid(user.getId());
            record.setClass_info(productionShift.get(0).getShift());
            record.setRecord_name(user.getNickname());
            record.setCraft_param(deviceParam);
            //数量根据工序计划查找
            record.setPcb_task_code(processTask.getPcb_task_code());
            //设备名称根据设备号查找
            Device device = deviceRepository.findbyDeviceCode(recordReq.getDeviceCode());
            record.setDevice_name(device.getDevice_name());
            record.setRecord_time(today);
            craftParameterRecordRepository.save(record);
            recordlist = new ArrayList<>();
            recordlist.add(record);
        }
        return ResultVoUtil.success(recordlist);
    }

    @Override
    public ResultVo qcCraftParameterRecord(CraftParameterRecordReq recordReq) {
        //根据id查找记录
        CraftParameterRecord craftParameterRecord = craftParameterRecordRepository.findById(recordReq.getId()).get();
        //查找员工
        User user = userRepository.findByCard_sequence(recordReq.getCardSequence());
        //qc
        craftParameterRecord.setFirstInspection_name(user.getNickname());
        craftParameterRecord.setQc_time(new Date());
        craftParameterRecordRepository.save(craftParameterRecord);
        return ResultVoUtil.success("操作成功");
    }
}
