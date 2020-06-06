package com.linln.modules.system.service;

import com.linln.common.enums.StatusEnum;
import com.linln.modules.system.domain.Role;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @author 小懒虫
 * @date 2018/8/14
 */
public interface RoleService {

    /**
     * 获取用户岗位列表
     * @param id 用户ID
     * @return 岗位列表
     */
    @Transactional
    Set<Role> getUserOkRoleList(Long id);

    /**
     * 判断指定的用户是否存在岗位
     * @param id 用户ID
     * @return 是否存在岗位
     */
    Boolean existsUserOk(Long id);

    /**
     * 根据岗位ID查询岗位数据
     * @param id 岗位ID
     * @return 岗位信息
     */
    @Transactional
    Role getById(Long id);

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<Role> getPageList(Example<Role> example);

    /**
     * 获取岗位列表数据
     * @param sort 排序对象
     * @return 岗位列表
     */
    List<Role> getListBySortOk(Sort sort);

    /**
     * 岗位标识是否重复
     * @param role 岗位实体类
     * @return 标识是否重复
     */
    boolean repeatByName(Role role);

    /**
     * 保存岗位
     * @param role 岗位实体类
     * @return 岗位信息
     */
    Role save(Role role);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     * @param statusEnum 数据状态
     * @param idList 数据ID列表
     * @return 操作结果
     */
    @Transactional
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);
}
