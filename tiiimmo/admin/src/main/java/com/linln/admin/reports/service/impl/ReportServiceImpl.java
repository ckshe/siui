package com.linln.admin.reports.service.impl;

import com.linln.admin.quality.repository.BadClassDetailRepository;
import com.linln.admin.reports.request.BadDetailReq;
import com.linln.admin.reports.service.ReportService;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import com.linln.utill.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private BadClassDetailRepository badClassDetailRepository ;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //不良记录报表
    @Override
    public ResultVo badDetailReport(BadDetailReq req) {

        StringBuffer wheresql = new StringBuffer( " where 1=1 ");

        if(req.getPcbTaskCode()!=null&&!"".equals(req.getPcbTaskCode())){
            wheresql.append(" and t1.pcb_task_code like '%" +
                    req.getPcbTaskCode() +
                    "%' ");
        }
        if(req.getPlateNo()!=null&&!"".equals(req.getPlateNo())){
            wheresql.append(" and t1.plate_no like '%" +
                    req.getPlateNo() +
                    "%' ");
        }


        if(req.getStartTime()!=null&&req.getEndTime()!=null){
            String startTime = req.getStartTime()+" 00:00:00";
            String endTime = req.getEndTime()+" 23:59:59";
            wheresql.append(" and t1.record_time > '" +
                    startTime +
                    "' and t1.record_time < '" +
                    endTime +
                    "'");

        }
        StringBuffer sql = new StringBuffer("SELECT\n" +
                "\t* \n" +
                "FROM\n" +
                "\t(\n" +
                "\tSELECT\n" +
                "\t\t*,\n" +
                "\t\tROW_NUMBER ( ) OVER ( ORDER BY t4.Id ASC ) row \n" +
                "\tFROM\n" +
                "\t\t( SELECT t1.*, t2.pcb_code FROM quality_badclass_detail t1 LEFT JOIN produce_process_task t2 ON t2.pcb_task_code = t1.pcb_task_code " +
                wheresql +
                ") t4 \n" +
                "\t) t3 \n");

        Integer page = 1;
        Integer size = 10;
        if(req.getPage()!=null&&req.getSize()!=null){
            page = req.getPage();
            size = req.getSize();
        }

        List<Map<String,Object>> count = jdbcTemplate.queryForList(sql.toString());

        sql.append("where t3.Row between " +
                ((page-1)*size+1) +
                " and " +
                (page*size) +
                "");

        List<Map<String,Object>> mapList = jdbcTemplate.queryForList(sql.toString());
        return ResultVoUtil.success("查询成功",mapList,count.size());
    }
}
