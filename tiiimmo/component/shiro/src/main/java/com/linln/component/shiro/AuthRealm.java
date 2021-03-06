package com.linln.component.shiro;

import com.linln.common.constant.AdminConst;
import com.linln.common.enums.StatusEnum;
import com.linln.modules.system.domain.Role;
import com.linln.modules.system.domain.User;
import com.linln.modules.system.service.RoleService;
import com.linln.modules.system.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

/**
 * @author 小懒虫
 * @date 2018/8/14
 */
public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    /**
     * 授权逻辑
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 获取用户Principal对象
        User user = (User) principal.getPrimaryPrincipal();

        // 管理员拥有所有权限
        if (user.getId().equals(AdminConst.ADMIN_ID)) {
            info.addRole(AdminConst.ADMIN_ROLE_NAME);
            info.addStringPermission("*:*:*");
            return info;
        }

        // 赋予岗位和资源授权
//        Set<Role> roles = ShiroUtil.getSubjectRoles();
//        Long roleId = roles.iterator().next().getId();
//        List<String> perms = roleService.selectPermsByRoleId(roleId);

        // 给员工授权
        Long subjectId = ShiroUtil.getSubject().getId();
        List<String> perms2 = userService.selectPermsByUserId(subjectId);

        //int size = perms.size() + perms2.size();
        System.out.println(perms2.size());


        //info.addStringPermissions(perms);
        info.addStringPermissions(perms2);


        /*roles.forEach(role -> {
            info.addRole(role.getName());
            role.getMenus().forEach(menu -> {
                String perms = menu.getPerms();
                if (menu.getStatus().equals(StatusEnum.OK.getCode())
                        && !StringUtils.isEmpty(perms) && !perms.contains("*")) {
                    info.addStringPermission(perms);
                }
            });
        });
        */
        return info;
    }

    /**
     * 认证逻辑
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // 获取数据库中的用户名密码
        //刷卡后台自动登录
        boolean isCardCrash = false;
        String username = token.getUsername();
        if (username.contains("|")){
            username = username.substring(0, username.lastIndexOf("|"));
            isCardCrash = true;
        }

        User user = userService.getByName(username);

        // 判断用户名是否存在
        if (user == null) {
            throw new UnknownAccountException();
        } else if (user.getStatus().equals(StatusEnum.FREEZED.getCode())) {
            throw new LockedAccountException();
        }


         SimpleAuthenticationInfo authenticationInfo = null;
        if (isCardCrash){
            authenticationInfo = new SimpleAuthenticationInfo(
                    user,            //用户名
                    ShiroUtil.encrypt("password", user.getSalt()),  //密码
                    ByteSource.Util.bytes(user.getSalt()),//使用账号作为盐值
                    getName());          //realm name
        }else {
            authenticationInfo = new SimpleAuthenticationInfo(
                    user,            //用户名
                    user.getPassword(),  //密码
                    ByteSource.Util.bytes(user.getSalt()),//使用账号作为盐值
                    getName());          //realm name
        }
        // 对盐进行加密处理
        ByteSource salt = ByteSource.Util.bytes(user.getSalt());

        /* 传入密码自动判断是否正确
         * 参数1：传入对象给Principal
         * 参数2：正确的用户密码
         * 参数3：加盐处理
         * 参数4：固定写法
         */
        //SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), salt, getName());

        return authenticationInfo;
    }

    /**
     * 自定义密码验证匹配器
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        setCredentialsMatcher(new SimpleCredentialsMatcher() {
            @Override
            public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
                UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
                SimpleAuthenticationInfo info = (SimpleAuthenticationInfo) authenticationInfo;
                // 获取明文密码及密码盐
                String password = String.valueOf(token.getPassword());
                String salt = CodecSupport.toString(info.getCredentialsSalt().getBytes());

                return equals(ShiroUtil.encrypt(password, salt), info.getCredentials());
            }
        });
    }
}
