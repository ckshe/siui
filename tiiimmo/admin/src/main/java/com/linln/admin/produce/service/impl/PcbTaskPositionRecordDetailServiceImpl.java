package com.linln.admin.produce.service.impl;

import com.linln.RespAndReqs.PcbTaskPositionRecordDetailReq;
import com.linln.RespAndReqs.PcbTaskReq;
import com.linln.admin.produce.domain.PcbTaskPositionRecordDetail;
import com.linln.admin.produce.repository.PcbTaskPositionRecordDetailRepositoty;
import com.linln.admin.produce.service.PcbTaskPositionRecordDetailService;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PcbTaskPositionRecordDetailServiceImpl implements PcbTaskPositionRecordDetailService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

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

    @Override
    public ResultVo findFinishRecordDetail(PcbTaskPositionRecordDetailReq req) {
        Integer page = req.getPage(); //当前页
        Integer size = req.getSize(); //每页条数
        String deviceCode = req.getDeviceCode();
        String pcbTaskCode = req.getPcbTaskCode();
        String processTaskCode = req.getProcessTaskCode();

        if(page == null||size == null){
            page = 1;
            size = 10;
        }

        StringBuffer wheresql = new StringBuffer(" ");
        if(deviceCode!=null&&!"".equals(deviceCode)){
            wheresql.append(" and device_code  like '" +
                    "%" + deviceCode + "%" +
                    "' ");
        }
        if(pcbTaskCode!=null&&!"".equals(pcbTaskCode)){
            wheresql.append(" and pcb_task_code  like '" +
                    "%" + pcbTaskCode + "%" +
                    "' ");
        }
        if(processTaskCode!=null&&!"".equals(processTaskCode)){
            wheresql.append(" and process_task_code  like '" +
                    "%" + processTaskCode + "%" +
                    "' ");
        }

        StringBuffer sql = new StringBuffer("select  *\n" +
                "                from (select row_number()\n" +
                "                over(order by device_code) as rownumber,*\n" +
                "                from produce_task_position_record_detail where install_status = 2" +
                wheresql.toString() +
                ") temp_row ");

        List<Map<String,Object>> count = jdbcTemplate.queryForList(sql.toString());
        sql.append(" where rownumber between " +
                ((page-1)*size+1) +
                " and " +
                (page*size) +
                "");

        List<Map<String,Object>> mapList = jdbcTemplate.queryForList(sql.toString());

        return ResultVoUtil.success("查询成功",mapList,count.size());

    }


}
