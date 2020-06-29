package com.linln.timer;

import com.linln.admin.produce.domain.ProcessTask;
import com.linln.admin.produce.domain.ProcessTaskStatusHistory;
import com.linln.admin.produce.repository.ProcessTaskRepository;
import com.linln.admin.produce.repository.ProcessTaskStatusHistoryRepository;
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

    @Scheduled(cron = "0 * * * * ?")
    public void autoCaculateWorkTime(){
        System.out.println("-------定时开始工时计算------------");
        List<ProcessTask> listNotFinish = processTaskRepository.findByNotFinish();
        for(ProcessTask p : listNotFinish){
            List<ProcessTaskStatusHistory> historyListist = processTaskStatusHistoryRepository.findByProcessTaskCode(p.getProcess_task_code());
            Integer workTime = 0;
            for(ProcessTaskStatusHistory history : historyListist){
                if(history.getEnd_time()!=null){
                    workTime = workTime + history.getContinue_time();
                }else {
                    Date today = new Date();
                    Long cha = (today.getTime()-history.getStart_time().getTime())/1000;
                    history.setContinue_time(Integer.parseInt(cha+""));
                    workTime = workTime + Integer.parseInt(cha+"");
                    processTaskStatusHistoryRepository.save(history);
                }
            }
            p.setWork_time(new BigDecimal(workTime));
            processTaskRepository.save(p);
        }
        System.out.println("-------定时工时计算结束------------");
    }
}
