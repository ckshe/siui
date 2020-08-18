package com.linln.admin.produce.service.impl;

import com.linln.RespAndReqs.PcbTaskReq;
import com.linln.admin.produce.domain.PcbTaskPositionRecordDetail;
import com.linln.admin.produce.repository.PcbTaskPositionRecordDetailRepositoty;
import com.linln.admin.produce.service.PcbTaskPositionRecordDetailService;
import com.linln.common.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PcbTaskPositionRecordDetailServiceImpl implements PcbTaskPositionRecordDetailService {

    @Autowired
    private PcbTaskPositionRecordDetailRepositoty recordDetailRepositoty;

    // 执行下料的操作的目的: 将之前上的替代料替换为原料
    // 执行下料的操作必备条件: 1.数据的状态是上料完成的 2.之前上的料是数据的替代料 (两个物料编码不同的情况)
    // 如果将替代料物料编码和替代料元件名清空则必须上原料 不清空还可以上其他替代料
    @Override
    public PcbTaskPositionRecordDetail downPositionRecordDetail(PcbTaskReq req) {
        PcbTaskPositionRecordDetail recordDetail = recordDetailRepositoty.findByProductCode(req.getProductCode());
        if (recordDetail.getInstall_status().equals("2") &&
                !recordDetail.getProduct_code().equals(recordDetail.getLast_product_code())){
            recordDetail.setInstall_status("0");
            //recordDetail.setLast_product_code(null);
            //recordDetail.setLast_element_name(null);
            recordDetailRepositoty.save(recordDetail);
        }
        return recordDetail;
    }
}
