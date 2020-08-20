package com.linln.admin.produceFrom.service.impl;

import com.linln.RespAndReqs.UserDeviceHistoryReq;
import com.linln.admin.produceFrom.service.UserDeviceHistoryService;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import com.linln.utill.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
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
        Date upStartTime = req.getUpStartTime();
        Date upFinishTime = req.getUpFinishTime();
        Date downStartTime = req.getDownStartTime();
        Date downFinishTime = req.getDownFinishTime();


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

        if(upStartTime!=null&&!"".equals(upStartTime)&&upFinishTime!=null&&!"".equals(upFinishTime)){
            String startTimeString = DateUtil.date2String(upStartTime,"yyyy-MM-dd") + " 00:00:00";
            String endTimeString = DateUtil.date2String(upFinishTime,"yyyy-MM-dd") + " 23:59:59";
            //startTimeString.substring(0,10);
            wheresql.append(" and up_time >= '" +
                    startTimeString  +
                    "' and up_time <= '" +
                    endTimeString +
                    "'");

        }
        if(downStartTime!=null&&!"".equals(downStartTime)&&downFinishTime!=null&&!"".equals(downFinishTime)){
            String startTimeString = DateUtil.date2String(downStartTime,"yyyy-MM-dd") + " 00:00:00";
            String endTimeString = DateUtil.date2String(downFinishTime,"yyyy-MM-dd") + " 23:59:59";
            //startTimeString.substring(0,10);
            wheresql.append(" and down_time >= '" +
                    startTimeString  +
                    "' and down_time <= '" +
                    endTimeString +
                    "'");

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
