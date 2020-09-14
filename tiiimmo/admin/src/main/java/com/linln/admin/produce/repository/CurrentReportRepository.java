package com.linln.admin.produce.repository;

import com.linln.admin.produce.domain.CurrentReport;
import com.linln.modules.system.repository.BaseRepository;
import org.apache.commons.beanutils.converters.StringArrayConverter;
import org.springframework.data.jpa.repository.Query;

public interface CurrentReportRepository extends BaseRepository<CurrentReport,Long> {

    @Query(value = "select * from produce_current_report where pcb_task_code = ?1 and report_type = ?2",nativeQuery = true)
    CurrentReport findByPcb_task_code(String pcbTaskCode,String reportType);

    @Query(value = "select * from produce_current_report where year_month = ?1 and report_type = ?2 ",nativeQuery = true)
    CurrentReport findByYear_monthAndReport_type(String yearMonth, String reporType);



}
