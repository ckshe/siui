package com.linln.modules.system.service.impl;

import com.linln.common.data.PageSort;
import com.linln.common.enums.StatusEnum;
import com.linln.modules.system.domain.Role;
import com.linln.modules.system.repository.RoleRepository;
import com.linln.modules.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @author 小懒虫
 * @date 2018/8/14
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    /**
     * 获取用户岗位列表
     * @param id 用户ID
     */
    @Override
    @Transactional
    public Set<Role> getUserOkRoleList(Long id) {
        Byte status = StatusEnum.OK.getCode();
        return roleRepository.findByUsers_IdAndStatus(id, status);
    }

    /**
     * 判断指定的用户是否存在岗位
     * @param id 用户ID
     */
    @Override
    public Boolean existsUserOk(Long id) {
        Byte status = StatusEnum.OK.getCode();
        return roleRepository.existsByUsers_IdAndStatus(id, status);
    }

    /**
     * 根据岗位ID查询岗位数据
     * @param id 岗位ID
     */
    @Override
    @Transactional
    public Role getById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<Role> getPageList(Example<Role> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest(Sort.Direction.ASC);
        return roleRepository.findAll(example, page);
    }

    /**
     * 获取岗位列表数据
     * @param sort 排序对象
     */
    @Override
    public List<Role> getListBySortOk(Sort sort) {
        return roleRepository.findAllByStatus(sort, StatusEnum.OK.getCode());
    }

    /**
     * 岗位标识是否重复
     * @param role 岗位实体类
     */
    @Override
    public boolean repeatByName(Role role) {
        Long id = role.getId() != null ? role.getId() : Long.MIN_VALUE;
        return roleRepository.findByNameAndIdNot(role.getName(), id) != null;
    }

    /**
     * 保存岗位
     * @param role 岗位实体类
     */
    @Override
    public Role save(Role role){
        return roleRepository.save(role);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> ids){
        // 删除岗位时取消与岗位和菜单的关联
        if(statusEnum == StatusEnum.DELETE){
            // 非规范的Jpa操作，直接采用SQL语句
            roleRepository.cancelUserJoin(ids);
            roleRepository.cancelMenuJoin(ids);
        }
        return roleRepository.updateStatus(statusEnum.getCode(), ids) > 0;
    }

    @Override
    public List<Role> getListByExample(Example<Role> example, Sort sort) {
        return roleRepository.findAll(example, sort);
    }

    @Override
    public List<Role> list() {
        return roleRepository.findAllRoles();
    }
}
