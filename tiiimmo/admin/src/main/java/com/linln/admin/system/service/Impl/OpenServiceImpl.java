package com.linln.admin.system.service.Impl;

import com.linln.admin.system.service.OpenService;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OpenServiceImpl implements OpenService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public ResultVo excuteSql(String sql) {


        List<Map<String,Object>> result = jdbcTemplate.queryForList(sql);
        return ResultVoUtil.success(result);
    }
}
