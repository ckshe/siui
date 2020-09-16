package com.linln.admin.produce.service.impl;

import com.linln.RespAndReqs.PcbTaskReq;
import com.linln.admin.base.domain.DeviceProductElement;
import com.linln.admin.base.domain.DeviceProductReplaceElement;
import com.linln.admin.base.repository.DeviceProductElementRepository;
import com.linln.admin.base.repository.DeviceProductReplaceElementRepository;
import com.linln.admin.produce.domain.PcbTaskPositionRecord;
import com.linln.admin.produce.domain.PcbTaskPositionRecordDetail;
import com.linln.admin.produce.domain.ProcessTask;
import com.linln.admin.produce.repository.PcbTaskPositionRecordDetailRepositoty;
import com.linln.admin.produce.repository.PcbTaskPositionRecordRepository;
import com.linln.admin.produce.repository.ProcessTaskRepository;
import com.linln.admin.produce.service.PcbTaskPositionRecordService;
import com.linln.common.enums.StatusEnum;
import com.linln.common.exception.ResultException;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import com.linln.modules.system.domain.User;
import com.linln.modules.system.service.UserService;
import javassist.bytecode.LineNumberAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
    private DeviceProductReplaceElementRepository deviceProductReplaceElementRepository;

    @Autowired
    private UserService userService;

    @Override
    public PcbTaskPositionRecord buildPositionRecordAndReturn(PcbTaskReq req) {
        PcbTaskPositionRecord record2 = recordRepository.findByProcess_task_code(req.getProcessTaskCode(),req.getDeviceCode());
        if(record2!=null){
            List<PcbTaskPositionRecordDetail> detailList2 = recordDetailRepositoty.findByProcess_task_codeAndDevice_code(req.getProcessTaskCode(),req.getDeviceCode());
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
        record.setPcb_task_code(processTask.getPcb_task_code());
        record.setDevice_code(req.getDeviceCode());
        record.setProcess_task_code(req.getProcessTaskCode());
        record.setRecord_status("0");
        //record.setStart_time(new Date());
        record.setStatus(StatusEnum.OK.getCode());
        record = recordRepository.save(record);
        List<PcbTaskPositionRecordDetail> detailList = new ArrayList<>();
        Map<String,String> map = new HashMap<>();

        for(DeviceProductElement element : elementList){
            //String key = element.getPcb_code()+element.getProduct_code();
            String key = element.getPcb_code()+element.getElement_name();
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
//            DeviceProductReplaceElement replaceElement = deviceProductReplaceElementRepository.queryByOriginalProductCode(detail.getProduct_code());
//            if (replaceElement != null){
//                detail.setLast_product_code(replaceElement.getReplace_product_code());
//                detail.setLast_element_name(replaceElement.getReplace_product_name());
//            }

            List<DeviceProductReplaceElement> elements = deviceProductReplaceElementRepository.queryByOriginalProductCodes(detail.getProduct_code());
            if (elements != null && elements.size() == 1){
                detail.setLast_product_code(elements.get(0).getReplace_product_code());
                detail.setLast_element_name(elements.get(0).getReplace_product_name());
            }
            if (elements != null && elements.size() == 2){
                detail.setLast_product_code(elements.get(0).getReplace_product_code() + "," + elements.get(1).getReplace_product_code());
                detail.setLast_element_name(elements.get(0).getReplace_product_name() + "," + elements.get(1).getReplace_product_name());
//                for (DeviceProductReplaceElement replaceElement : elements) {
//                    detail.setLast_product_code(replaceElement.getReplace_product_code());
//                    detail.setLast_element_name(replaceElement.getReplace_product_name());
//
//                }
            }
            if (elements != null && elements.size() == 3){
                detail.setLast_product_code(elements.get(0).getReplace_product_code() + "," + elements.get(1).getReplace_product_code() + "," + elements.get(2).getReplace_product_code());
                detail.setLast_element_name(elements.get(0).getReplace_product_name() + "," + elements.get(1).getReplace_product_name() + "," + elements.get(2).getReplace_product_name());
            }


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
        PcbTaskPositionRecord record = recordRepository.findByProcess_task_code(req.getProcessTaskCode(),req.getDeviceCode());
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
        record.setUser_name(req.getUsername());
        recordRepository.save(record);
        return ResultVoUtil.success("操作成功");
    }

    /*@Override
    public ResultVo confirmScanProductCode(PcbTaskReq pcbTaskReq) {
        PcbTaskPositionRecordDetail detail = recordDetailRepositoty.findByProcess_task_codeAndProduct_code(pcbTaskReq.getProcessTaskCode(),pcbTaskReq.getLastProductCode());
        Map<String,Object> map = new HashMap<>();
        if(detail == null){
            map.put("status","3");
            map.put("msg","找不到该原物料");
            return ResultVoUtil.success(map);
        }
        detail.setProduct_code(pcbTaskReq.getProductCode());
        detail.setLast_product_code(pcbTaskReq.getLastProductCode());
        detail.setInstall_status("1");
        recordDetailRepositoty.save(detail);
        map.put("status","1");
        map.put("msg","扫描成功");
        return ResultVoUtil.success(map);
    }

    @Override
    public ResultVo scanProductCode(PcbTaskReq req) {
        PcbTaskPositionRecordDetail detail = recordDetailRepositoty.findByProcess_task_codeAndProduct_code(req.getProcessTaskCode(),req.getProductCode());
        Map<String,Object> map = new HashMap<>();
        if(detail==null){
            //找不到物料则寻找替代料表
            List<DeviceProductReplaceElement> elementList = deviceProductReplaceElementRepository.findByReplace_prodcut_code(req.getProductCode());
            //如果只有一个唯一替代料则直接替代，重写数据
            if(elementList.size()==1){
                detail = recordDetailRepositoty.findByProcess_task_codeAndProduct_code(req.getProcessTaskCode(),elementList.get(0).getOriginal_product_code());
                detail.setLast_product_code(detail.getProduct_code());
                detail.setProduct_code(elementList.get(0).getReplace_product_code());
                detail.setInstall_status("1");
                recordDetailRepositoty.save(detail);
                map.put("status","1");
                map.put("msg","扫描成功");
            }
            //
            if(elementList.size()==0){
                map.put("status","3");
                map.put("msg","找不到该物料");
            }
            //多于一个返回供选择
            if(elementList.size()>1){
                map.put("elementList",elementList);
                map.put("status","4");
                map.put("msg","该替代料原物料不唯一，请选择要替代的原物料");
            }
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
    }*/

    @Override
    public ResultVo finishPositionRecord(PcbTaskReq req) {

        PcbTaskPositionRecord record = recordRepository.findByProcess_task_code(req.getProcessTaskCode(),req.getDeviceCode());
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


    @Override
    public ResultVo getDetailByProductCode(String productCode) {
        PcbTaskPositionRecordDetail detail = recordDetailRepositoty.findByProduct_code(productCode);
        if(detail==null){
            return ResultVoUtil.error("查询不到该信息");
        }
        return ResultVoUtil.success(detail);
    }


    @Override
    public ResultVo confirmScanProductCode(PcbTaskReq pcbTaskReq) {
        PcbTaskPositionRecordDetail detail = recordDetailRepositoty.findByProcess_task_codeAndProduct_code(pcbTaskReq.getProcessTaskCode(),pcbTaskReq.getLastProductCode(),pcbTaskReq.getDeviceCode());
        Map<String,Object> map = new HashMap<>();
        if(detail == null){
            map.put("status","3");
            map.put("msg","找不到该原物料");
            return ResultVoUtil.success(map);
        }
        detail.setProduct_code(pcbTaskReq.getProductCode());
        detail.setLast_product_code(pcbTaskReq.getLastProductCode());
        detail.setInstall_status("1");
        recordDetailRepositoty.save(detail);
        map.put("status","1");
        map.put("msg","扫描成功");
        return ResultVoUtil.success(map);
    }

    /* V1.0 pda扫描物料接口
    @Override
    public ResultVo scanProductCode(PcbTaskReq req) {

        PcbTaskPositionRecordDetail detail = recordDetailRepositoty.findByProcess_task_codeAndProduct_code(req.getProcessTaskCode(),req.getProductCode(),req.getDeviceCode());
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
    }*/

    @Override
    public ResultVo scanProductCode(PcbTaskReq req) {
        PcbTaskPositionRecordDetail detail = recordDetailRepositoty.findByProcess_task_codeAndProduct_code(req.getProcessTaskCode(),req.getProductCode(),req.getDeviceCode());
        Map<String,Object> map = new HashMap<>();
        if(detail==null){
            //找不到物料则寻找替代料表
            List<DeviceProductReplaceElement> elementList = deviceProductReplaceElementRepository.findByReplace_prodcut_code(req.getProductCode());
            //如果只有一个唯一替代料则直接替代，重写数据
            if(elementList.size()==1){
                detail = recordDetailRepositoty.findByProcess_task_codeAndProduct_code(req.getProcessTaskCode(),elementList.get(0).getOriginal_product_code(),req.getDeviceCode());
                //detail.setLast_product_code(elementList.get(0).getReplace_product_code()); // 替代料的物料编号
                //detail.setProduct_code(elementList.get(0).getReplace_product_code());
                //detail.setElement_name(elementList.get(0).getReplace_product_name()); // 替代料的元件号
                // 这里准确的应该是获取扫描的物料码,并通过扫描的物料码查询到对应的元件名 (替代料唯一时可以直接获取替代料的物料编号和元件名)

                if (detail == null){
                    ResultVoUtil.error("找不到该物料");
                } else {
                    detail.setLast_product_code(elementList.get(0).getReplace_product_code());
                    detail.setLast_element_name(elementList.get(0).getReplace_product_name());
                    if (detail.getInstall_status().equals("0")){
                        detail.setInstall_status("1");
                    }

                    recordDetailRepositoty.save(detail);
                    map.put("status","1");
                    map.put("msg","扫描成功");

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
                }
            }

//                if (StringUtils.hasLength(elementList.get(0).getReplace_product_name())
//                        && StringUtils.hasLength(elementList.get(0).getReplace_product_code())
//                        && ! detail.getLast_product_code().equals(elementList.get(0).getReplace_product_code())
//                        && ! detail.getLast_element_name().equals(elementList.get(0).getReplace_product_name())){
//                    detail.setLast_product_code(elementList.get(0).getReplace_product_code());
//                    detail.setLast_element_name(elementList.get(0).getReplace_product_name());
//                }

//                if (! detail.getLast_product_code().equals(elementList.get(0).getReplace_product_code())
//                    && ! detail.getLast_element_name().equals(elementList.get(0).getReplace_product_name())){
//                    System.out.println(111111);
//                }


                //detail.setLast_product_code(req.getPcbTaskCode());
//                List<DeviceProductReplaceElement> replace_prodcut_code = deviceProductReplaceElementRepository.findByReplace_prodcut_code(detail.getLast_product_code());
//                detail.setLast_element_name(replace_prodcut_code.get(0).getReplace_product_name());

                //if(detail.getProduct_code().equals(detail.getLast_product_code())){
                    //detail.setInstall_status("1");
                //}
                // 状态为0的数据,更新状态为1进行上料,上料完成的不更改detail状态

//                if (detail.getInstall_status().equals("0")){
//                    detail.setInstall_status("1");
//                }
//
//                recordDetailRepositoty.save(detail);
//                map.put("status","1");
//                map.put("msg","扫描成功");
//
//                if("1".equals(detail.getInstall_status())){
//
//                    map.put("status","1");
//                    map.put("msg","该物料已扫描");
//                    return ResultVoUtil.success(map);
//                }
//                if("2".equals(detail.getInstall_status())){
//                    map.put("status","2");
//                    map.put("msg","该物料已插入");
//                    return ResultVoUtil.success(map);
//                }



            //扫描替代料后没有找到该替代料对应的物料编号 (物料不存在的情况)
            if(elementList.size()==0){
                map.put("status","3");
                map.put("msg","找不到该物料");
            }
            //多于一个返回供选择
            if(elementList.size()>1){
                if (req.getProductCode().equals(elementList.get(0).getReplace_product_code())) {
                    detail = recordDetailRepositoty.findByProcess_task_codeAndProduct_code(req.getProcessTaskCode(), elementList.get(0).getOriginal_product_code(), req.getDeviceCode());
                    if (detail == null) {
                        ResultVoUtil.error("找不到该物料");
                    } else {
                        detail.setLast_product_code(elementList.get(0).getReplace_product_code());
                        detail.setLast_element_name(elementList.get(0).getReplace_product_name());
                        if (detail.getInstall_status().equals("0")) {
                            detail.setInstall_status("1");
                        }

                        recordDetailRepositoty.save(detail);
                        map.put("status", "1");
                        map.put("msg", "扫描成功");

                        if ("1".equals(detail.getInstall_status())) {
                            map.put("status", "1");
                            map.put("msg", "该物料已扫描");
                            return ResultVoUtil.success(map);
                        }
                        if ("2".equals(detail.getInstall_status())) {
                            map.put("status", "2");
                            map.put("msg", "该物料已插入");
                            return ResultVoUtil.success(map);
                        }
                    }
                }

                if (req.getProductCode().equals(elementList.get(1).getReplace_product_code())) {
                    detail = recordDetailRepositoty.findByProcess_task_codeAndProduct_code(req.getProcessTaskCode(), elementList.get(1).getOriginal_product_code(), req.getDeviceCode());
                    if (detail == null) {
                        ResultVoUtil.error("找不到该物料");
                    } else {
                        detail.setLast_product_code(elementList.get(1).getReplace_product_code());
                        detail.setLast_element_name(elementList.get(1).getReplace_product_name());
                        if (detail.getInstall_status().equals("0")) {
                            detail.setInstall_status("1");
                        }

                        recordDetailRepositoty.save(detail);
                        map.put("status", "1");
                        map.put("msg", "扫描成功");

                        if ("1".equals(detail.getInstall_status())) {
                            map.put("status", "1");
                            map.put("msg", "该物料已扫描");
                            return ResultVoUtil.success(map);
                        }
                        if ("2".equals(detail.getInstall_status())) {
                            map.put("status", "2");
                            map.put("msg", "该物料已插入");
                            return ResultVoUtil.success(map);
                        }
                    }
                }

                if (req.getProductCode().equals(elementList.get(2).getReplace_product_code())) {
                    detail = recordDetailRepositoty.findByProcess_task_codeAndProduct_code(req.getProcessTaskCode(), elementList.get(2).getOriginal_product_code(), req.getDeviceCode());
                    if (detail == null) {
                        ResultVoUtil.error("找不到该物料");
                    } else {
                        detail.setLast_product_code(elementList.get(2).getReplace_product_code());
                        detail.setLast_element_name(elementList.get(2).getReplace_product_name());
                        if (detail.getInstall_status().equals("0")) {
                            detail.setInstall_status("1");
                        }

                        recordDetailRepositoty.save(detail);
                        map.put("status", "1");
                        map.put("msg", "扫描成功");

                        if ("1".equals(detail.getInstall_status())) {
                            map.put("status", "1");
                            map.put("msg", "该物料已扫描");
                            return ResultVoUtil.success(map);
                        }
                        if ("2".equals(detail.getInstall_status())) {
                            map.put("status", "2");
                            map.put("msg", "该物料已插入");
                            return ResultVoUtil.success(map);
                        }
                    }
                }


//                PcbTaskPositionRecordDetail detail1 = recordDetailRepositoty.findByProcess_task_codeAndProduct_code(req.getProcessTaskCode(),elementList.get(0).getOriginal_product_code(),req.getDeviceCode());
//                PcbTaskPositionRecordDetail detail2 = recordDetailRepositoty.findByProcess_task_codeAndProduct_code(req.getProcessTaskCode(),elementList.get(1).getOriginal_product_code(),req.getDeviceCode());
//                //PcbTaskPositionRecordDetail detail3 = recordDetailRepositoty.findByProcess_task_codeAndProduct_code(req.getProcessTaskCode(),elementList.get(2).getOriginal_product_code(),req.getDeviceCode());
//                if (! detail1.getInstall_status().equals("2")){
//                    if (detail1 == null){
//                        ResultVoUtil.error("找不到该物料");
//                    } else {
//                        detail1.setLast_product_code(elementList.get(0).getReplace_product_code());
//                        detail1.setLast_element_name(elementList.get(0).getReplace_product_name());
//                        if (detail1.getInstall_status().equals("0")){
//                            detail1.setInstall_status("1");
//                        }
//
//                        recordDetailRepositoty.save(detail1);
//                        map.put("status","1");
//                        map.put("msg","扫描成功");
//
//                        if("1".equals(detail1.getInstall_status())){
//                            map.put("status","1");
//                            map.put("msg","该物料已扫描");
//                            return ResultVoUtil.success(map);
//                        }
//                        if("2".equals(detail1.getInstall_status())){
//                            map.put("status","2");
//                            map.put("msg","该物料已插入");
//                            return ResultVoUtil.success(map);
//                        }
//                    }
//                } else {
//                    if (! detail2.getInstall_status().equals("2")){
//                        if (detail2 == null){
//                            ResultVoUtil.error("找不到该物料");
//                        } else {
//                            detail2.setLast_product_code(elementList.get(1).getReplace_product_code());
//                            detail2.setLast_element_name(elementList.get(1).getReplace_product_name());
//                            if (detail2.getInstall_status().equals("0")){
//                                detail2.setInstall_status("1");
//                            }
//
//                            recordDetailRepositoty.save(detail2);
//                            map.put("status","1");
//                            map.put("msg","扫描成功");
//
//                            if("1".equals(detail2.getInstall_status())){
//
//                                map.put("status","1");
//                                map.put("msg","该物料已扫描");
//                                return ResultVoUtil.success(map);
//                            }
//                            if("2".equals(detail2.getInstall_status())){
//                                map.put("status","2");
//                                map.put("msg","该物料已插入");
//                                return ResultVoUtil.success(map);
//                            }
//                        }
//                    }
//                }

//                map.put("elementList",elementList);
//                map.put("status","4");
//                map.put("msg","该替代料原物料不唯一，请选择要替代的原物料");
            }
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
        detail.setLast_product_code(detail.getProduct_code());
        detail.setLast_element_name(detail.getElement_name());
        recordDetailRepositoty.save(detail);
        map.put("status","1");
        map.put("msg","扫描成功");
        return ResultVoUtil.success(map);
    }

    /*@Override
    public PcbTaskPositionRecord buildPositionRecordAndReturn(PcbTaskReq req) {
        PcbTaskPositionRecord record2 = recordRepository.findByProcess_task_code(req.getProcessTaskCode(),req.getDeviceCode());
        if(record2!=null){
            List<PcbTaskPositionRecordDetail> detailList2 = recordDetailRepositoty.findByProcess_task_codeAndDevice_code(req.getProcessTaskCode(),req.getDeviceCode());
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
        record.setPcb_task_code(processTask.getPcb_task_code());
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
    }*/
}
