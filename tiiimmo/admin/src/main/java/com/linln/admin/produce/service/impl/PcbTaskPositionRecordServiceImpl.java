package com.linln.admin.produce.service.impl;

import com.linln.RespAndReqs.PcbTaskReq;
import com.linln.admin.base.domain.DeviceProductElement;
import com.linln.admin.base.repository.DeviceProductElementRepository;
import com.linln.admin.produce.domain.PcbTaskPositionRecord;
import com.linln.admin.produce.domain.PcbTaskPositionRecordDetail;
import com.linln.admin.produce.domain.ProcessTask;
import com.linln.admin.produce.repository.PcbTaskPositionRecordDetailRepositoty;
import com.linln.admin.produce.repository.PcbTaskPositionRecordRepository;
import com.linln.admin.produce.repository.ProcessTaskRepository;
import com.linln.admin.produce.service.PcbTaskPositionRecordService;
import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import com.linln.modules.system.domain.User;
import com.linln.modules.system.service.UserService;
import javassist.bytecode.LineNumberAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PcbTaskPositionRecordServiceImpl implements PcbTaskPositionRecordService {

    @Autowired
    private DeviceProductElementRepository deviceProductElementRepository;
    @Autowired
    private PcbTaskPositionRecordRepository recordRepository;

    @Autowired
    private ProcessTaskRepository processTaskRepository;

    @Autowired
    private PcbTaskPositionRecordDetailRepositoty recordDetailRepositoty;

    @Autowired
    private UserService userService;

    @Override
    public PcbTaskPositionRecord buildPositionRecordAndReturn(PcbTaskReq req) {
        PcbTaskPositionRecord record2 = recordRepository.findByProcess_task_code(req.getProcessTaskCode());
        if(record2!=null){
            List<PcbTaskPositionRecordDetail> detailList2 = recordDetailRepositoty.findByProcess_task_code(req.getProcessTaskCode());
            record2.setDetailList(detailList2);
            return record2;
        }
        ProcessTask processTask = processTaskRepository.findByProcessTaskCode(req.getProcessTaskCode());
        String a_or_b = "";
        if(processTask.getProcess_name().contains("A")){
            a_or_b = "A";
        }else if(processTask.getProcess_name().contains("B")) {
            a_or_b = "B";
        }
        List<DeviceProductElement> elementList = deviceProductElementRepository.findByDevice_code(req.getDeviceCode(),req.getPcbId(),a_or_b);
        PcbTaskPositionRecord record = new PcbTaskPositionRecord();
        record.setPcb_task_code(req.getPcbTaskCode());
        record.setDevice_code(req.getDeviceCode());
        record.setProcess_task_code(req.getProcessTaskCode());
        record.setRecord_status("0");
        //record.setStart_time(new Date());
        record.setStatus(StatusEnum.OK.getCode());
        record = recordRepository.save(record);
        List<PcbTaskPositionRecordDetail> detailList = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        for(DeviceProductElement element : elementList){
            String key = element.getPcb_code()+element.getProduct_code();
            if(map.containsKey(key)){
                continue;
            }else {
                map.put(key,"");
            }
            PcbTaskPositionRecordDetail detail = new PcbTaskPositionRecordDetail();
            detail.setRecord_id(record.getId());
            detail.setDevice_code(record.getDevice_code());
            detail.setElement_name(element.getElement_name());
            detail.setInstall_status("0");
            detail.setPcb_task_code(record.getPcb_task_code());
            detail.setProcess_task_code(record.getProcess_task_code());
            detail.setPosition(element.getPosition());
            detail.setProduct_code(element.getProduct_code());
            detail.setStatus(StatusEnum.OK.getCode());
            detailList.add(detail);
        }
        recordDetailRepositoty.saveAll(detailList);
        record.setDetailList(detailList);
        return record;
    }

    @Override
    public List<PcbTaskPositionRecordDetail> getPositionRecord(PcbTaskReq req) {

        List<PcbTaskPositionRecordDetail> detailList = recordDetailRepositoty.findByProcess_task_code(req.getProcessTaskCode());

        return detailList;
    }


    @Override
    public ResultVo startPositonRecord(PcbTaskReq req) {
        PcbTaskPositionRecord record = recordRepository.findByProcess_task_code(req.getProcessTaskCode());
        if(record==null){
            return ResultVoUtil.error("找不到该计划");
        }
        if("1".equals(record.getRecord_status())){
            return ResultVoUtil.error("已开始");
        }
        if("2".equals(record.getRecord_status())){
            return ResultVoUtil.error("已结束");
        }
        record.setRecord_status("1");
        record.setStart_time(new Date());
        record.setUser_name(record.getUser_name());
        recordRepository.save(record);
        return ResultVoUtil.success("操作成功");
    }

    @Override
    public ResultVo scanProductCode(PcbTaskReq req) {

        PcbTaskPositionRecordDetail detail = recordDetailRepositoty.findByProcess_task_codeAndProduct_code(req.getProcessTaskCode(),req.getProductCode());
        Map<String,String> map = new HashMap<>();
        if(detail==null){
            map.put("status","3");
            map.put("msg","找不到该物料");
            return ResultVoUtil.success(map);
        }
        if("1".equals(detail.getInstall_status())){
            map.put("status","1");
            map.put("msg","该物料已扫描");
            return ResultVoUtil.success(map);
        }
        if("2".equals(detail.getInstall_status())){
            map.put("status","2");
            map.put("msg","该物料已插入");
            return ResultVoUtil.success(map);
        }
        detail.setInstall_status("1");
        recordDetailRepositoty.save(detail);
        map.put("status","1");
        map.put("msg","扫描成功");
        return ResultVoUtil.success(map);
    }

    @Override
    public ResultVo finishPositionRecord(PcbTaskReq req) {

        PcbTaskPositionRecord record = recordRepository.findByProcess_task_code(req.getProcessTaskCode());
        if(record==null){
            return ResultVoUtil.error("找不到该计划");
        }
        if("0".equals(record.getRecord_status())){
            return ResultVoUtil.error("未开始");
        }
        if("2".equals(record.getRecord_status())){
            return ResultVoUtil.error("已结束");
        }
        record.setRecord_status("2");
        record.setFinish_time(new Date());
        recordRepository.save(record);
        return ResultVoUtil.success("操作成功");

    }

    @Override
    public ResultVo getUserInfoByCard(String cardSequence) {
        User user = userService.findUserByCardNo(cardSequence);
        if(user==null){
            return ResultVoUtil.error("查询不到该工号");

        }
        return ResultVoUtil.success(user);
    }
}
