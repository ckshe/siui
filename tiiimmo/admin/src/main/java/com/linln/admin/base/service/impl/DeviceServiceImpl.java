package com.linln.admin.base.service.impl;

import com.linln.RespAndReqs.DeviceReq;
import com.linln.admin.base.domain.Device;
import com.linln.admin.base.domain.DeviceType;
import com.linln.admin.base.repository.DeviceRepository;
import com.linln.admin.base.repository.DeviceTypeRepository;
import com.linln.admin.base.repository.ProcessRepository;
import com.linln.admin.base.service.DeviceService;
import com.linln.common.data.PageSort;
import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author www
 * @date 2020/05/14
 */
@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private DeviceTypeRepository deviceTypeRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public Device getById(Long id) {
        return deviceRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<Device> getPageList(Example<Device> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return deviceRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param device 实体对象
     */
    @Override
    public Device save(Device device) {
        return deviceRepository.save(device);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return deviceRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }

    @Override
    public ResultVo getDeviceByProcess(DeviceReq req) {
        //List<Device> devices = deviceRepository.findAllByBelong_process(req.getProcessName());
        List<Device> devices = deviceRepository.findAll();
        return ResultVoUtil.success(devices);
    }

    @Override
    public ResultVo getDeviceByProcessType(String countType) {

        StringBuffer sql = new StringBuffer("SELECT device_type FROM base_device GROUP BY device_type order by device_type desc");
        List<Map<String, Object>> typeList = jdbcTemplate.queryForList(sql.toString());
        List<Map<String,Object>> result = new ArrayList<>();
        for(int i = 0;i<typeList.size();i++){
            String type = (String)typeList.get(i).get("device_type");
            if(type!=null){
                type = type.trim();
            }
            if("0".equals(countType)&&!"贴片".equals(type)){
                continue;
            }
            if("1".equals(countType)&&"贴片".equals(type)){
                continue;
            }
            StringBuffer deviceListByTypeSql = new StringBuffer("SELECT device_code+'\\'+device_name as title,id ,device_code,device_name from base_device WHERE device_type = '" +
                    type +
                    "' ");
            if(type==null){
                deviceListByTypeSql = new StringBuffer("SELECT device_code+'\\'+device_name as title,id ,device_code,device_name from base_device WHERE device_type is null ");
                type = "OTHER";
            }
            List<Map<String, Object>> mapList = jdbcTemplate.queryForList(deviceListByTypeSql.toString());
            Map<String,Object> map = new HashMap<>();
            map.put("title",type);
            map.put("id",i);
            map.put("children",mapList);
            result.add(map);
        }



        return ResultVoUtil.success(result);
    }

    @Override
    public List<Device> list() {
        return deviceRepository.findAllbyDeviceSort();
    }

}
