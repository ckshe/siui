package com.linln.admin.produce.service.impl;

import com.linln.admin.produce.domain.CurrentReport;
import com.linln.admin.produce.repository.CurrentReportRepository;
import com.linln.admin.produce.service.CurrentReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrentReportServiceImpl implements CurrentReportService {

    @Autowired
    private CurrentReportRepository currentReportRepository;
    @Override
    public void addCurrentReport(CurrentReport currentReport) {
        currentReportRepository.save(currentReport);
    }
}
