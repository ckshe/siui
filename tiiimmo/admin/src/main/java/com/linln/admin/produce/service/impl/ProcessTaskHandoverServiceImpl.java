package com.linln.admin.produce.service.impl;

import com.linln.admin.produce.domain.ProcessTaskHandover;
import com.linln.admin.produce.repository.ProcessTaskHandoverRepository;
import com.linln.admin.produce.request.ProcessTaskHandoverReq;
import com.linln.admin.produce.service.ProcessTaskHandoverService;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import com.linln.modules.system.domain.User;
import com.linln.modules.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProcessTaskHandoverServiceImpl implements ProcessTaskHandoverService {

    @Autowired
    private ProcessTaskHandoverRepository processTaskHandoverRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addProceeTaskHandover(ProcessTaskHandover handover) {
        if(handover.getCardSequence()!=null){
            User user = userRepository.findByCard_sequence(handover.getCardSequence());
            handover.setUser_name(user.getNickname());
        }
        handover.setCreate_time(new Date());
        processTaskHandoverRepository.save(handover);
    }

    @Override
    public ResultVo findPRocessTaskHandover(ProcessTaskHandoverReq req) {

        List<ProcessTaskHandover> halist = processTaskHandoverRepository.findPRocessTaskHandover(req.getProcessTaskCode(),req.getDeviceCode());

        return ResultVoUtil.success(halist);
    }
}
