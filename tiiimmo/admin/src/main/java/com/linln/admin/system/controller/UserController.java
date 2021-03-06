package com.linln.admin.system.controller;

import com.linln.admin.system.validator.UserValid;
import com.linln.common.constant.AdminConst;
import com.linln.common.enums.ResultEnum;
import com.linln.common.enums.StatusEnum;
import com.linln.common.exception.ResultException;
import com.linln.common.utils.*;
import com.linln.common.vo.ResultVo;
import com.linln.component.actionLog.action.StatusAction;
import com.linln.component.actionLog.action.UserAction;
import com.linln.component.actionLog.annotation.ActionLog;
import com.linln.component.actionLog.annotation.EntityParam;
import com.linln.component.excel.ExcelUtil;
import com.linln.component.fileUpload.config.properties.UploadProjectProperties;
import com.linln.component.shiro.ShiroUtil;
import com.linln.modules.system.domain.Menu;
import com.linln.modules.system.domain.Role;
import com.linln.modules.system.domain.User;
import com.linln.modules.system.repository.UserRepository;
import com.linln.modules.system.service.MenuService;
import com.linln.modules.system.service.RoleService;
import com.linln.modules.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 小懒虫
 * @date 2018/8/14
 */
@Controller
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("system:user:index")
    public String index(Model model, User user) {

        // 获取用户列表
        Page<User> list = userService.getPageList(user);
//        Set<Role> roles = user.getRoles();
//        String jsonRole = JsonUtil.set2json(roles);
//        System.out.println(jsonRole);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        model.addAttribute("dept", user.getDept());
        return "/system/user/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("system:user:add")
    public String toAdd() {
        return "/system/user/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("system:user:edit")
    public String toEdit(@PathVariable("id") User user, Model model) {
        model.addAttribute("user", user);
        return "/system/user/edit";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     * @param user 实体对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"system:user:add"})
    @ResponseBody
    @ActionLog(key = UserAction.USER_SAVE, action = UserAction.class)
    public ResultVo save(@Validated UserValid valid, @EntityParam User user) {

        // 验证数据是否合格
        if (user.getId() == null) {

            // 判断密码是否为空
            if (user.getPassword().isEmpty() || "".equals(user.getPassword().trim())) {
                throw new ResultException(ResultEnum.USER_PWD_NULL);
            }

            // 判断两次密码是否一致
            if (!user.getPassword().equals(valid.getConfirm())) {
                throw new ResultException(ResultEnum.USER_INEQUALITY);
            }

            // 对密码进行加密
            String salt = ShiroUtil.getRandomSalt();
            String encrypt = ShiroUtil.encrypt(user.getPassword(), salt);
            user.setPassword(encrypt);
            user.setSalt(salt);
        }

        // 判断用户名是否重复
        if (userService.repeatByUsername(user)) {
            throw new ResultException(ResultEnum.USER_EXIST);
        }

        // 复制保留无需修改的数据
        if (user.getId() != null) {
            // 不允许操作超级管理员数据
            if (user.getId().equals(AdminConst.ADMIN_ID) &&
                    !ShiroUtil.getSubject().getId().equals(AdminConst.ADMIN_ID)) {
                throw new ResultException(ResultEnum.NO_ADMIN_AUTH);
            }

            User beUser = userService.getById(user.getId());
            String[] fields = {"password", "salt", "picture", "roles"};
            EntityBeanUtil.copyProperties(beUser, user, fields);
        }


        // 保存数据
        userService.save(user);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     * @param user 实体对象
     */
    @PostMapping("/save2")
    @RequiresPermissions({"system:user:edit"})
    @ResponseBody
    @ActionLog(key = UserAction.USER_SAVE, action = UserAction.class)
    public ResultVo save2(@Validated UserValid valid, @EntityParam User user) {



        // 复制保留无需修改的数据
        if (user.getId() != null) {
            // 不允许操作超级管理员数据
            if (user.getId().equals(AdminConst.ADMIN_ID) &&
                    !ShiroUtil.getSubject().getId().equals(AdminConst.ADMIN_ID)) {
                throw new ResultException(ResultEnum.NO_ADMIN_AUTH);
            }

            User beUser = userService.getById(user.getId());
            String[] fields = {"password", "salt", "picture", "roles"};
            EntityBeanUtil.copyProperties(beUser, user, fields);
        }


        String role = userService.getRole(user.getId());
        if (role != null && !"".equals(role)){
            user.setClassNo(role);
        }

        // 保存数据
        userService.save(user);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("system:user:detail")
    public String toDetail(@PathVariable("id") User user, Model model) {
        model.addAttribute("user", user);
        return "/system/user/detail";
    }

    /**
     * 跳转到修改密码页面
     */
    @GetMapping("/pwd")
    @RequiresPermissions("system:user:pwd")
    public String toEditPassword(Model model, @RequestParam(value = "ids", required = false) List<Long> ids) {
        model.addAttribute("idList", ids);
        return "/system/user/pwd";
    }

    /**
     * 修改密码
     */
    @PostMapping("/pwd")
    @RequiresPermissions("system:user:pwd")
    @ResponseBody
    @ActionLog(key = UserAction.EDIT_PWD, action = UserAction.class)
    public ResultVo editPassword(String password, String confirm,
                                 @RequestParam(value = "ids", required = false) List<Long> ids,
                                 @RequestParam(value = "ids", required = false) List<User> users) {

        // 判断密码是否为空
        if (password.isEmpty() || "".equals(password.trim())) {
            throw new ResultException(ResultEnum.USER_PWD_NULL);
        }

        // 判断两次密码是否一致
        if (!password.equals(confirm)) {
            throw new ResultException(ResultEnum.USER_INEQUALITY);
        }

        // 不允许操作超级管理员数据
        if (ids.contains(AdminConst.ADMIN_ID) &&
                !ShiroUtil.getSubject().getId().equals(AdminConst.ADMIN_ID)) {
            throw new ResultException(ResultEnum.NO_ADMIN_AUTH);
        }

        // 修改密码，对密码进行加密
        users.forEach(user -> {
            String salt = ShiroUtil.getRandomSalt();
            String encrypt = ShiroUtil.encrypt(password, salt);
            user.setPassword(encrypt);
            user.setSalt(salt);
        });

        // 保存数据
        userService.save(users);
        return ResultVoUtil.success("修改成功");
    }

    /**
     * 跳转到岗位分配页面
     */
    @GetMapping("/role")
    @RequiresPermissions("system:user:role")
    public String toRole(@RequestParam(value = "ids") User user, Model model) {
        // 获取指定用户岗位列表
        Set<Role> authRoles = user.getRoles();
        // 获取全部岗位列表
        Sort sort = new Sort(Sort.Direction.ASC, "createDate");
        List<Role> list = roleService.getListBySortOk(sort);

        model.addAttribute("id", user.getId());
        model.addAttribute("list", list);
        model.addAttribute("authRoles", authRoles);
        return "/system/user/role";
    }

    /**
     * 保存岗位分配信息
     */
    @PostMapping("/role")
    @RequiresPermissions("system:user:role")
    @ResponseBody
    @ActionLog(key = UserAction.EDIT_ROLE, action = UserAction.class)
    public ResultVo auth(
            @RequestParam(value = "id", required = true) User user,
            @RequestParam(value = "roleId", required = false) HashSet<Role> roles) {

        // 不允许操作超级管理员数据
        if (user.getId().equals(AdminConst.ADMIN_ID) &&
                !ShiroUtil.getSubject().getId().equals(AdminConst.ADMIN_ID)) {
            throw new ResultException(ResultEnum.NO_ADMIN_AUTH);
        }

        // 更新用户岗位
        user.setRoles(roles);
        user.setClassNo(roles.iterator().next().getTitle());
//        if (roles.iterator().next().getTitle() != null){
//            user.setClassNo(roles.iterator().next().getTitle());
//        }


        // 保存数据
        userService.save(user);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 获取用户头像
     */
    @GetMapping("/picture")
    public void picture(String p, HttpServletResponse response) throws IOException {
        String defaultPath = "/images/user-picture.jpg";
        if (!(StringUtils.isEmpty(p) || p.equals(defaultPath))) {
            UploadProjectProperties properties = SpringContextUtil.getBean(UploadProjectProperties.class);
            String fuPath = properties.getFilePath();
            String spPath = properties.getStaticPath().replace("*", "");
            File file = new File(fuPath + p.replace(spPath, ""));
            if (file.exists()) {
                FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
                return;
            }
        }
        Resource resource = new ClassPathResource("static" + defaultPath);
        FileCopyUtils.copy(resource.getInputStream(), response.getOutputStream());
    }

    /**
     * 导出用户数据
     */
    @GetMapping("/export")
    @RequiresPermissions("system:user:export")
    @ResponseBody
    public void exportExcel() {
        UserRepository userRepository = SpringContextUtil.getBean(UserRepository.class);
        ExcelUtil.exportExcel(User.class, userRepository.findAll());
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("system:user:status")
    @ResponseBody
    @ActionLog(name = "用户状态", action = StatusAction.class)
    public ResultVo updateStatus(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {

        // 不能修改超级管理员状态
        if (ids.contains(AdminConst.ADMIN_ID)) {
            throw new ResultException(ResultEnum.NO_ADMIN_STATUS);
        }

        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (userService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }

    /**
     * 查询员工对象
     * @return 员工集合
     */
    @GetMapping("/queryUsers")
    @ResponseBody
    public ResultVo queryUsers(){
        List<User> users = userService.findAll();
        if (users != null) {
            return ResultVoUtil.success("查询成功",users,userService.getSize().intValue());
        } else {
            return ResultVoUtil.error( 400,"查询失败");
        }
    }

    /**
     * 跳转到授权页面
     */
    @GetMapping("/auth")
    //@RequiresPermissions("system:user:auth")
    public String toAuth(@RequestParam(value = "ids") Long id, Model model){
        model.addAttribute("id", id);
        return "/system/user/auth";
    }

    @Autowired
    private MenuService menuService;

    /**
     * 获取权限资源列表
     */
    @GetMapping("/authList")
    //@RequiresPermissions("system:user:auth")
    @ResponseBody
    public ResultVo authList(@RequestParam(value = "ids") User user){
        // 获取指定岗位权限资源
        Set<Menu> authMenus = user.getMenus();
        // 获取全部菜单列表
        List<Menu> list = menuService.getListBySortOk();
        // 融合两项数据
        list.forEach(menu -> {
            if(authMenus.contains(menu)){
                menu.setRemark("auth:true");
            }else {
                menu.setRemark("");
            }
        });
        return ResultVoUtil.success(list);
    }

    /**
     * 保存授权信息
     */
    @PostMapping("/auth")
    //@RequiresPermissions("system:user:auth")
    @ResponseBody
    @ActionLog(key = UserAction.EDIT_MENU, action = UserAction.class)
    public ResultVo menuAuth(
            @RequestParam(value = "id", required = true) User user,
            @RequestParam(value = "authId", required = false) HashSet<Menu> menus){
        // 不允许操作管理员岗位数据
        if (user.getId().equals(AdminConst.ADMIN_ID) &&
                !ShiroUtil.getSubject().getId().equals(AdminConst.ADMIN_ID)){
            throw new ResultException(ResultEnum.NO_ADMINROLE_AUTH);
        }

        // 更新岗位菜单
        user.setMenus(menus);

        // 保存数据
        userService.save(user);
        return ResultVoUtil.SAVE_SUCCESS;
    }
    /*@GetMapping("/queryUsers")
    @ResponseBody
    public ResultVo queryUsers(User user){
        Page<User> users = userService.getPageList(user);
        if (users != null) {
            return ResultVoUtil.success("查询成功",users);
        } else {
            return ResultVoUtil.error( 400,"查询失败");
        }
    }*/


    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     * @param user 实体对象
     */
    /*@PostMapping("/save")
    @RequiresPermissions({"system:user:add", "system:user:edit"})
    @ResponseBody
    @ActionLog(key = UserAction.USER_SAVE, action = UserAction.class)
    public ResultVo save(@Validated UserValid valid, @EntityParam User user) {

        // 验证数据是否合格
        if (user.getId() == null) {

            // 判断密码是否为空
            if (user.getPassword().isEmpty() || "".equals(user.getPassword().trim())) {
                throw new ResultException(ResultEnum.USER_PWD_NULL);
            }

            // 判断两次密码是否一致
            if (!user.getPassword().equals(valid.getConfirm())) {
                throw new ResultException(ResultEnum.USER_INEQUALITY);
            }

            // 对密码进行加密
            String salt = ShiroUtil.getRandomSalt();
            String encrypt = ShiroUtil.encrypt(user.getPassword(), salt);
            user.setPassword(encrypt);
            user.setSalt(salt);
        }

        // 判断用户名是否重复
        if (userService.repeatByUsername(user)) {
            throw new ResultException(ResultEnum.USER_EXIST);
        }

        // 复制保留无需修改的数据
        if (user.getId() != null) {
            // 不允许操作超级管理员数据
            if (user.getId().equals(AdminConst.ADMIN_ID) &&
                    !ShiroUtil.getSubject().getId().equals(AdminConst.ADMIN_ID)) {
                throw new ResultException(ResultEnum.NO_ADMIN_AUTH);
            }

            User beUser = userService.getById(user.getId());
            String[] fields = {"password", "salt", "picture", "roles"};
            EntityBeanUtil.copyProperties(beUser, user, fields);
        }


        if (user.getRoles().iterator().next().getTitle() != null){
            user.setClassNo(user.getRoles().iterator().next().getTitle());
        }

        // 保存数据
        userService.save(user);
        return ResultVoUtil.SAVE_SUCCESS;
    }*/


}
