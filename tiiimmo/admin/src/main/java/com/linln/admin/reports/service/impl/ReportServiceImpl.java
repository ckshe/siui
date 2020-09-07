package com.linln.admin.reports.service.impl;

import com.linln.admin.produce.domain.CurrentReport;
import com.linln.admin.produce.repository.CurrentReportRepository;
import com.linln.admin.quality.repository.BadClassDetailRepository;
import com.linln.admin.reports.request.BadDetailReq;
import com.linln.admin.reports.request.ClassInfoReq;
import com.linln.admin.reports.request.CurrentReportReq;
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



    @Override
    public ResultVo findCurrentReport(CurrentReportReq req) {

        StringBuffer wheresql = new StringBuffer( " where 1=1 ");

        if(req.getPcbTaskCode()!=null&&!"".equals(req.getPcbTaskCode())){
            wheresql.append(" and t1.pcb_task_code like '%" +
                    req.getPcbTaskCode() +
                    "%' ");
        }
        if(req.getPcbCode()!=null&&!"".equals(req.getPcbCode())){
            wheresql.append(" and t1.pcb_code like '%" +
                    req.getPcbCode() +
                    "%' ");
        }
        if(req.getProcessTaskCode()!=null&&!"".equals(req.getProcessTaskCode())){
            wheresql.append(" and t1.process_task_code like '%" +
                    req.getProcessTaskCode() +
                    "%' ");
        }
        if(req.getReportType()!=null&&!"".equals(req.getReportType())){
            wheresql.append(" and t1.report_type like '%" +
                    req.getReportType() +
                    "%' ");
        }
        if(req.getTaskSheetCode()!=null&&!"".equals(req.getTaskSheetCode())){
            wheresql.append(" and t1.task_sheet_code like '%" +
                    req.getTaskSheetCode() +
                    "%' ");
        }

        StringBuffer sql = new StringBuffer("SELECT\n" +
                "\t* \n" +
                "FROM\n" +
                "\t( SELECT *, ROW_NUMBER ( ) OVER ( ORDER BY t4.Id ASC ) row FROM ( SELECT t1.* FROM produce_current_report t1 " +
                wheresql +
                ") t4 ) t3 \n");

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

    @Override
    public ResultVo findClassInfo(ClassInfoReq req) {
        //produce_user_device_history

        StringBuffer wheresql = new StringBuffer( " where 1=1 ");

        if(req.getShift()!=null&&!"".equals(req.getShift())){
            wheresql.append(" and t1.class_info like '%" +
                    req.getShift() +
                    "%' ");
        }



        if(req.getStartTime()!=null&&req.getEndTime()!=null&&!"".equals(req.getEndTime())&&!"".equals(req.getStartTime())){
            String startTime = req.getStartTime()+" 00:00:00";
            String endTime = req.getEndTime()+" 23:59:59";
            wheresql.append(" and t1.up_time > '" +
                    startTime +
                    "' and t1.up_time < '" +
                    endTime +
                    "'");

        }

        StringBuffer sql = new StringBuffer("SELECT\n" +
                "\t* \n" +
                "FROM\n" +
                "\t( SELECT *, ROW_NUMBER ( ) OVER ( ORDER BY t4.Id ASC ) row FROM ( SELECT t1.* FROM produce_user_device_history t1 " +
                wheresql +
                ") t4 ) t3 \n");

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


        if(req.getStartTime()!=null&&req.getEndTime()!=null&&!"".equals(req.getEndTime())&&!"".equals(req.getStartTime())){
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
                "\t\t( SELECT t1.*, t2.pcb_id as pcb_code FROM quality_badclass_detail t1 LEFT JOIN produce_pcb_task t2 ON t2.pcb_task_code = t1.pcb_task_code " +
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
