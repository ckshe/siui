package com.linln.modules.system.repository;

import com.linln.modules.system.domain.Dept;
import com.linln.modules.system.domain.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author 小懒虫
 * @date 2018/8/14
 */
public interface UserRepository extends BaseRepository<User, Long>, JpaSpecificationExecutor<User> {

    /**
     * 根据用户名查询用户数据
     * @param username 用户名
     * @return 用户数据
     */
    public User findByUsername(String username);

    /**
     * 根据用户名查询用户数据,且排查指定ID的用户
     * @param username 用户名
     * @param id 排除的用户ID
     * @return 用户数据
     */
    public User findByUsernameAndIdNot(String username, Long id);

    /**
     * 查找多个相应部门的用户列表
     * @param dept 部门对象
     * @return 用户列表
     */
    public List<User> findByDept(Dept dept);

    /**
     * 删除多条数据
     * @param ids ID列表
     * @return 影响行数
     */
    public Integer deleteByIdIn(List<Long> ids);

    @Query(value = "select   * from sys_user where card_sequence =?1 ",nativeQuery = true)
    public User findByCard_sequence(String cardSequence);

    @Query(value = "select * from sys_user where nickname =?1 ",nativeQuery = true)
    User queryByUserName(String userName);

    @Query(value = "select class_no from sys_user where id =?1 ",nativeQuery = true)
    String queryById(Long id);

    @Query(value = "select perms from sys_menu where id in (select menu_id FROM sys_user_menu where user_id = ?1)", nativeQuery = true)
    List<String> selectPermsByUserId(Long subjectId);

    @Query(value = "select * from sys_user where status = 1 ",nativeQuery = true)
    List<User> findAllUsers();
}
