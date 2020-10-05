package com.linln.timer;

import com.linln.admin.base.domain.Device;
import com.linln.admin.base.repository.DeviceRepository;
import com.linln.admin.produce.domain.*;
import com.linln.admin.produce.repository.*;
import com.linln.admin.produce.service.PcbTaskService;
import com.linln.admin.quality.domain.DeviceStatusRecord;
import com.linln.admin.system.service.OpenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ProcesssTaskWorkTimeTimer {
    @Autowired
    private ProcessTaskRepository processTaskRepository;

    @Autowired
    private ProcessTaskStatusHistoryRepository processTaskStatusHistoryRepository;

    @Autowired
    private UserDeviceHistoryRepository userDeviceHistoryRepository;

    @Autowired
    private DeviceStatusRecordRepository deviceStatusRecordRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private OpenService openService;

    @Autowired
    private PcbTaskService pcbTaskService;

    @Autowired
    private ProcessTaskDetailDeviceRepository processTaskDetailDeviceRepository;

    @Value("${backup-path}")
    private String backupPath;

    //定时计算工时
    @Scheduled(cron = "0 * * * * ?")
    public void autoCaculateWorkTime() {
        System.out.println("-------定时开始工时计算------------");
        List<ProcessTask> listNotFinish = processTaskRepository.findByNotFinish();
        for (ProcessTask p : listNotFinish) {
            List<ProcessTaskStatusHistory> historyListist = processTaskStatusHistoryRepository.findByProcessTaskCode(p.getProcess_task_code());
            Integer workTime = 0;
            BigDecimal tempWorkTime = p.getWork_time();
            for (ProcessTaskStatusHistory history : historyListist) {
                if (history.getProcess_task_status().contains("生产中")) {
                    if (history.getEnd_time() != null) {
                        workTime = workTime + history.getContinue_time();
                    } else {
                        Date today = new Date();
                        Long cha = (today.getTime() - history.getStart_time().getTime()) / (1000 * 60);
                        history.setContinue_time(Integer.parseInt(cha + ""));
                        workTime = workTime + Integer.parseInt(cha + "");
                        processTaskStatusHistoryRepository.save(history);
                    }
                }
            }
            p.setWork_time(new BigDecimal(workTime));
            processTaskRepository.save(p);
        }
        System.out.println("-------定时工时计算结束------------");
    }

    @Scheduled(cron = "* * 1 * * ?")
    public void autoDownDevice() {
        System.out.println("-------凌晨1点自动下机开始------------");
        List<UserDeviceHistory> list = userDeviceHistoryRepository.findOnlyUpTimeRecordList();
        Date nowdate = new Date();
        list.forEach(history -> history.setDown_time(nowdate));
        userDeviceHistoryRepository.saveAll(list);
        System.out.println("-------凌晨1点自动下机结束------------");
    }


    //机台硬件接口心跳包，五分钟无调用则自动停机
    @Scheduled(cron = "0 0/5 * * * ? ")
    public void autoStopDevice() {
        System.out.println("-------五分钟不响应自动停机开始------------");
        Date today = new Date();

        List<Device> deviceList = deviceRepository.findAll();
        for (Device device : deviceList) {
            List<DeviceStatusRecord> recordList = deviceStatusRecordRepository.findByDevice_codeLastOne(device.getDevice_code());
            if (recordList != null && recordList.size() != 0) {
                DeviceStatusRecord record = recordList.get(0);
                long endtime = record.getEnd_time().getTime();
                long now = today.getTime();
                long dur = now - endtime;
                long temp = 1000 * 60 * 5;
                //五分钟无接口响应则新增结束
                if (dur > temp) {
                    if (!"2".equals(record.getDevice_status())) {
                        device.setDevice_status("2");
                        //当最后一条不为结束状态则新增一条结束状态
                        DeviceStatusRecord aRecord = new DeviceStatusRecord();
                        BeanUtils.copyProperties(record, aRecord);
                        aRecord.setId(null);
                        aRecord.setStart_time(today);
                        aRecord.setEnd_time(today);
                        aRecord.setDevice_status("2");
                        aRecord.setContinue_time(0);
                        deviceStatusRecordRepository.save(aRecord);
                        deviceRepository.save(device);
                    }
                }
            }
        }
        System.out.println("-------五分钟不响应自动停机结束------------");

    }


    //每天三点自动备份数据库
    @Scheduled(cron = "0 0 3 * * ?")
    public void autoBackUpDatabase() {
        openService.callCmd(backupPath);
    }

    //每一小时同步erp数据
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void autoUpdateErp() {
        pcbTaskService.getPcbTaskFromERP(null);
    }


    //每45分钟检测是否有变动
    @Scheduled(cron = "0 0/5 * * * ?")
    public void autoPauseProcessTaskStatus() {
        log.info("====45分钟暂停工单====");
        // 处理生产中的贴片任务
        List<ProcessTask> processTaskList = processTaskRepository.findByProducing();
        processTaskList = processTaskList.stream().filter(e -> e.getCount_type() == 0).collect(Collectors.toList());
        Date today = new Date();
        for (ProcessTask task : processTaskList) {
            Integer lastAmount = task.getLast_amount() == null ? 0 : task.getLast_amount();
            if (!lastAmount.equals(task.getAmount_completed())) {
                task.setLast_amount(task.getAmount_completed());
                task.setLast_count_time(today);
                processTaskRepository.save(task);
            } else {
                //相等则改为暂停状态
                if (task.getLast_count_time()!=null){
                    long timeContinue = (today.getTime() - task.getLast_count_time().getTime()) / (1000 * 60);
                    if (timeContinue<30){
                        continue;
                    }
                }else {
                    task.setLast_count_time(today);
                    processTaskRepository.save(task);
                    continue;
                }
                task.setProcess_task_status("暂停");
                log.info(task.getProcess_task_code());
                ProcessTaskStatusHistory history = processTaskStatusHistoryRepository.findByProcessTaskCodeLastRecord(task.getProcess_task_code());
                if (history!=null){
                    history.setEnd_time(today);
                    Long cha = (today.getTime() - history.getStart_time().getTime()) / (1000 * 60);
                    history.setContinue_time(Integer.parseInt(cha + ""));
                    processTaskStatusHistoryRepository.save(history);
                }
                //新增
                ProcessTaskStatusHistory newRecord = new ProcessTaskStatusHistory();
                newRecord.setContinue_time(0);
                newRecord.setStart_time(today);
                newRecord.setDevice_code(task.getDevice_code());
                newRecord.setDevice_name(task.getDevice_name());
                newRecord.setProcess_task_status("暂停");
                newRecord.setProcess_name(task.getProcess_name());
                newRecord.setProcess_task_code(task.getProcess_task_code());
                //newRecord.setEnd_time(today);
                processTaskStatusHistoryRepository.save(newRecord);
                processTaskRepository.save(task);
            }
        }
        // 处理生产中的工站任务
        /*List<ProcessTaskDetailDevice> processTaskDetailDevices = processTaskDetailDeviceRepository.findWorkTasks("生产中");
        for (ProcessTaskDetailDevice processTaskDetailDevice : processTaskDetailDevices) {
            Integer lastAmount = processTaskDetailDevice.getLast_amount() == null ? 0 : processTaskDetailDevice.getLast_amount();
            if (!lastAmount.equals(processTaskDetailDevice.getFinish_count())) {
                processTaskDetailDevice.setLast_amount(processTaskDetailDevice.getFinish_count());
                processTaskDetailDeviceRepository.save(processTaskDetailDevice);
            } else {
                //相等则改为暂停状态
                log.info("===>"+processTaskDetailDevice.toString());
                processTaskDetailDevice.setDevice_detail_status("暂停");
                ProcessTaskStatusHistory history = processTaskStatusHistoryRepository.findByProcessTaskCodeAndDeviceLastRecord(processTaskDetailDevice.getProcess_task_code(), processTaskDetailDevice.getDevice_code());
                Date today = new Date();
                if (history!=null){
                    history.setEnd_time(today);
                    Long cha = (today.getTime() - history.getStart_time().getTime()) / (1000 * 60);
                    history.setContinue_time(Integer.parseInt(cha + ""));
                    processTaskStatusHistoryRepository.save(history);
                }
                //新增
                ProcessTaskStatusHistory newRecord = new ProcessTaskStatusHistory();
                newRecord.setContinue_time(0);
                newRecord.setStart_time(today);
                newRecord.setDevice_code(processTaskDetailDevice.getDevice_code());
                newRecord.setProcess_task_status("暂停");
                newRecord.setProcess_task_code(processTaskDetailDevice.getProcess_task_code());
                //newRecord.setEnd_time(today);
                processTaskStatusHistoryRepository.save(newRecord);
                processTaskDetailDeviceRepository.save(processTaskDetailDevice);
            }
        }*/
    }
}
