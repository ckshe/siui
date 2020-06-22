package com.linln.admin.base.service.impl;

import com.linln.admin.base.domain.DeviceTechnology;
import com.linln.admin.base.repository.DeviceTechnologyRepository;
import com.linln.admin.base.service.DeviceTechnologyService;
import com.linln.common.data.PageSort;
import com.linln.common.enums.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author www
 * @date 2020/05/14
 */
@Service
public class DeviceTechnologyServiceImpl implements DeviceTechnologyService {

    @Autowired
    private DeviceTechnologyRepository deviceTechnologyRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public DeviceTechnology getById(Long id) {
        return deviceTechnologyRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<DeviceTechnology> getPageList(Example<DeviceTechnology> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        Sort sort = new Sort(Sort.Direction.ASC,"\\Qsort_no\\E");
        PageRequest pageRequest = new PageRequest(page.getPageNumber(),page.getPageSize(),sort);
        return deviceTechnologyRepository.findAll(example, pageRequest);
    }

    /**
     * 保存数据
     * @param deviceTechnology 实体对象
     */
    @Override
    public DeviceTechnology save(DeviceTechnology deviceTechnology) {
        return deviceTechnologyRepository.save(deviceTechnology);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return deviceTechnologyRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }

    @Override
    public List<DeviceTechnology> list() {
        return deviceTechnologyRepository.findAll();
    }

    @Override
    public List<String> queryDeviceTechnologyCode() {
        return deviceTechnologyRepository.queryDeviceTechnologyCode();
    }

    @Override
    public void moveDown(Long id) {
        DeviceTechnology deviceTechnology = deviceTechnologyRepository.findByDId(id);
        DeviceTechnology deviceTechnologyNext = deviceTechnologyRepository.moveDown(deviceTechnology.getSort_no());
        if (deviceTechnologyNext == null){
            return;
        }
        Integer temp = deviceTechnology.getSort_no();
        deviceTechnology.setSort_no(deviceTechnologyNext.getSort_no());
        deviceTechnologyNext.setSort_no(temp);

        deviceTechnologyRepository.save(deviceTechnology);
        deviceTechnologyRepository.save(deviceTechnologyNext);
    }

    @Override
    public void moveUp(Long id) {
        DeviceTechnology deviceTechnology = deviceTechnologyRepository.findByDId(id);
        DeviceTechnology deviceTechnologyBefore = deviceTechnologyRepository.moveUp(deviceTechnology.getSort_no());
        if (deviceTechnologyBefore == null){
            return;
        }
        Integer temp = deviceTechnology.getSort_no();
        deviceTechnology.setSort_no(deviceTechnologyBefore.getSort_no());
        deviceTechnologyBefore.setSort_no(temp);

        deviceTechnologyRepository.save(deviceTechnology);
        deviceTechnologyRepository.save(deviceTechnologyBefore);
    }
}