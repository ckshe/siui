package com.linln.modules.system.repository;

import com.linln.modules.system.domain.Role;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @author 小懒虫
 * @date 2018/8/14
 */
public interface RoleRepository extends BaseRepository<Role,Long> {

    /**
     * 查找多个岗位
     * @param ids id列表
     * @return 岗位列表
     */
    public List<Role> findByIdIn(List<Long> ids);

    /**
     * 查找相应状态的岗位
     * @param sort 排序对象
     * @param status 数据状态
     * @return 岗位列表
     */
    public List<Role> findAllByStatus(Sort sort, Byte status);

    /**
     * 查询指定用户的岗位列表
     * @param id 用户ID
     * @param status 岗位状态
     * @return 岗位列表
     */
    public Set<Role> findByUsers_IdAndStatus(Long id, Byte status);

    /**
     * 根据标识查询岗位数据,且排查指定ID的岗位
     * @param name 岗位标识
     * @param id 岗位ID
     * @return 岗位信息
     */
    public Role findByNameAndIdNot(String name, Long id);

    /**
     * 判断指定的用户是否存在岗位
     * @param id 用户ID
     * @param status 岗位状态
     * @return 是否存在岗位
     */
    public Boolean existsByUsers_IdAndStatus(Long id, Byte status);

    /**
     * 取消岗位与用户之间的关系
     * @param ids 岗位ID
     * @return 影响结果
     */
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM sys_user_role WHERE role_id in ?1", nativeQuery = true)
    public Integer cancelUserJoin(List<Long> ids);

    /**
     * 取消岗位与菜单之间的关系
     * @param ids 岗位ID
     * @return 影响结果
     */
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM sys_role_menu WHERE role_id in ?1", nativeQuery = true)
    public Integer cancelMenuJoin(List<Long> ids);

    @Query(value = "select title FROM sys_role", nativeQuery = true)
    List<Role> findAllRoles();

    @Query(value = "select perms from sys_menu where id in (select menu_id FROM sys_role_menu where role_id = ?1)", nativeQuery = true)
    List<String> selectPermsByRoleId(Long roleId);
}
