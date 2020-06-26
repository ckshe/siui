package com.linln.admin.base.service.impl;

import com.linln.admin.base.domain.Device;
import com.linln.admin.base.domain.Program;
import com.linln.admin.base.repository.DeviceRepository;
import com.linln.admin.base.repository.ProgramRepository;
import com.linln.admin.base.service.ProgramService;
import com.linln.common.data.PageSort;
import com.linln.common.enums.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 连
 * @date 2020/06/09
 */
@Service
public class ProgramServiceImpl implements ProgramService {

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public Program getById(Long id) {
        return programRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<Program> getPageList(Example<Program> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return programRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param program 实体对象
     */
    @Override
    public Program save(Program program) {
        // 获取设备编号
        String deviceNo = program.getCode();
        // 根据设备编号查询出设备信息
        Device device = deviceRepository.queryByDeviceNo(deviceNo);
        program.setDeviceId(device);

        return programRepository.save(program);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return programRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}