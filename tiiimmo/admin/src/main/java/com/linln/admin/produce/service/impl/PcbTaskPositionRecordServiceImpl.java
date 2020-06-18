package com.linln.admin.produce.service.impl;

import com.linln.RespAndReqs.PcbTaskReq;
import com.linln.admin.base.domain.DeviceProductElement;
import com.linln.admin.base.repository.DeviceProductElementRepository;
import com.linln.admin.produce.domain.PcbTaskPositionRecord;
import com.linln.admin.produce.domain.PcbTaskPositionRecordDetail;
import com.linln.admin.produce.repository.PcbTaskPositionRecordDetailRepositoty;
import com.linln.admin.produce.repository.PcbTaskPositionRecordRepository;
import com.linln.admin.produce.service.PcbTaskPositionRecordService;
import com.linln.common.enums.StatusEnum;
import javassist.bytecode.LineNumberAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PcbTaskPositionRecordServiceImpl implements PcbTaskPositionRecordService {

    @Autowired
    private DeviceProductElementRepository deviceProductElementRepository;
    @Autowired
    private PcbTaskPositionRecordRepository recordRepository;

    @Autowired
    private PcbTaskPositionRecordDetailRepositoty recordDetailRepositoty;

    @Override
    public void buildPositionRecord(PcbTaskReq req) {
        List<DeviceProductElement> elementList = deviceProductElementRepository.findByDevice_code(req.getDeviceCode());
        PcbTaskPositionRecord record = new PcbTaskPositionRecord();
        record.setPcb_task_code(req.getPcbTaskCode());
        record.setDevice_code(req.getDeviceCode());
        record.setRecord_status("0");
        record.setStart_time(new Date());
        record.setStatus(StatusEnum.OK.getCode());
        record = recordRepository.save(record);
        List<PcbTaskPositionRecordDetail> detailList = new ArrayList<>();
        for(DeviceProductElement element : elementList){
            PcbTaskPositionRecordDetail detail = new PcbTaskPositionRecordDetail();
            detail.setRecord_id(record.getId());
            detail.setDevice_code(record.getDevice_code());
            detail.setElement_name(element.getElement_name());
            detail.setInstall_status("0");
            detail.setPcb_task_code(record.getPcb_task_code());
            detail.setPosition(element.getPosition());
            detail.setProduct_code(element.getProduct_code());
            detail.setStatus(StatusEnum.OK.getCode());
            detailList.add(detail);
        }
        recordDetailRepositoty.saveAll(detailList);

    }
}
