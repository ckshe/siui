package com.linln.admin.produceFrom.service.impl;

import com.linln.RespAndReqs.UserDeviceHistoryReq;
import com.linln.admin.produceFrom.service.UserDeviceHistoryService;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserDeviceHistoryServiceImpl implements UserDeviceHistoryService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public ResultVo findDeviceHistory(UserDeviceHistoryReq req) {
        Integer page = req.getPage(); //当前页
        Integer size = req.getSize(); //每页条数
        String deviceCode = req.getDeviceCode(); // 设备编号
        String processTaskCode = req.getProcessTaskCode(); // 工序任务号
        String userName = req.getUserName();
        // 页数和页面大小为空时,设置默认值
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
        if(processTaskCode!=null&&!"".equals(processTaskCode)){
            wheresql.append(" and process_task_code  like '" +
                    "%" + processTaskCode + "%" +
                    "' ");
        }
        if(userName!=null&&!"".equals(userName)){
            wheresql.append(" and user_name  like '" +
                    "%" + userName + "%" +
                    "' ");
        }

        StringBuffer sql = new StringBuffer("select  *\n" +
                "                from (select row_number()\n" +
                "                over(order by up_time desc) as rownumber,*\n" +
                "                from produce_user_device_history where  process_task_code != '未分配'" +
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
