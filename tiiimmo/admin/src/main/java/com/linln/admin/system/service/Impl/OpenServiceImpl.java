package com.linln.admin.system.service.Impl;

import com.linln.admin.system.service.OpenService;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
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

    @Override
    public  void callCmd(String locationCmd){
        try {
            Process child = Runtime.getRuntime().exec("cmd.exe /C start "+locationCmd);
            InputStream in = child.getInputStream();
            int c;
            while ((c = in.read()) != -1) {
            }
            in.close();
            try {
                child.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
