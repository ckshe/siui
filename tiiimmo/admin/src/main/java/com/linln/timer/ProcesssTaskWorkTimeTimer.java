package com.linln.timer;

import com.linln.admin.base.domain.Device;
import com.linln.admin.base.repository.DeviceRepository;
import com.linln.admin.produce.domain.ProcessTask;
import com.linln.admin.produce.domain.ProcessTaskStatusHistory;
import com.linln.admin.produce.domain.UserDeviceHistory;
import com.linln.admin.produce.repository.DeviceStatusRecordRepository;
import com.linln.admin.produce.repository.ProcessTaskRepository;
import com.linln.admin.produce.repository.ProcessTaskStatusHistoryRepository;
import com.linln.admin.produce.repository.UserDeviceHistoryRepository;
import com.linln.admin.quality.domain.DeviceStatusRecord;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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

    //定时计算工时
    @Scheduled(cron = "0 * * * * ?")
    public void autoCaculateWorkTime(){
        System.out.println("-------定时开始工时计算------------");
        List<ProcessTask> listNotFinish = processTaskRepository.findByNotFinish();
        for(ProcessTask p : listNotFinish){
            List<ProcessTaskStatusHistory> historyListist = processTaskStatusHistoryRepository.findByProcessTaskCode(p.getProcess_task_code());
            Integer workTime = 0;
            BigDecimal tempWorkTime = p.getWork_time();
            for(ProcessTaskStatusHistory history : historyListist){
                if(history.getProcess_task_status().contains("进行中")||history.getProcess_task_status().contains("生产中")){
                    if(history.getEnd_time()!=null){
                        workTime = workTime + history.getContinue_time();
                    }else {
                        Date today = new Date();
                        Long cha = (today.getTime()-history.getStart_time().getTime())/(1000*60);
                        history.setContinue_time(Integer.parseInt(cha+""));
                        workTime = workTime + Integer.parseInt(cha+"");
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
    public void autoDownDevice(){
        System.out.println("-------凌晨1点自动下机开始------------");
        List<UserDeviceHistory> list = userDeviceHistoryRepository.findOnlyUpTimeRecordList();
        Date nowdate = new Date();
        list.forEach(history -> history.setDown_time(nowdate));
        userDeviceHistoryRepository.saveAll(list);
        System.out.println("-------凌晨1点自动下机结束------------");
    }


    //机台硬件接口心跳包，五分钟无调用则自动停机
    @Scheduled(cron = "0 0/5 * * * ? ")
    public void autoStopDevice(){
        System.out.println("-------五分钟不响应自动停机开始------------");

        List<Device> deviceList = deviceRepository.findAll();
        for(Device device : deviceList){
            List<DeviceStatusRecord> recordList = deviceStatusRecordRepository.findByDevice_codeLastOne(device.getDevice_code());
            if(recordList!=null&&recordList.size()!=0){
                DeviceStatusRecord record = recordList.get(0);
                long endtime = record.getEnd_time().getTime();
                long now = (new Date()).getTime();
                long dur = now - endtime;
                long temp = 1000*60*5;
                //五分钟无接口响应则新增结束
                if(dur>temp){
                    if(!"2".equals(record.getDevice_status())){
                        //当最后一条不为结束状态则新增一条结束状态
                        DeviceStatusRecord aRecord = new DeviceStatusRecord();
                        BeanUtils.copyProperties(record,aRecord);
                        aRecord.setId(null);
                        aRecord.setStart_time(record.getEnd_time());
                        aRecord.setEnd_time(record.getEnd_time());
                        aRecord.setDevice_status("2");
                        deviceStatusRecordRepository.save(aRecord);
                    }
                }
            }
        }
        System.out.println("-------五分钟不响应自动停机结束------------");

    }
}
