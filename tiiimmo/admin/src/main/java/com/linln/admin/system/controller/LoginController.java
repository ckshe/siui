package com.linln.admin.system.controller;

import com.linln.RespAndReqs.CardLoginReq;
import com.linln.RespAndReqs.DeviceReq;
import com.linln.admin.base.domain.Device;
import com.linln.admin.base.repository.DeviceRepository;
import com.linln.admin.produce.domain.ProcessTaskDevice;
import com.linln.admin.produce.domain.UserDeviceHistory;
import com.linln.admin.produce.repository.ProcessTaskDeviceRepository;
import com.linln.admin.produce.repository.UserDeviceHistoryRepository;
import com.linln.common.config.properties.ProjectProperties;
import com.linln.common.data.URL;
import com.linln.common.enums.ResultEnum;
import com.linln.common.enums.StatusEnum;
import com.linln.common.exception.ResultException;
import com.linln.common.utils.CaptchaUtil;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.utils.SpringContextUtil;
import com.linln.common.vo.ResultVo;
import com.linln.component.actionLog.action.UserAction;
import com.linln.component.actionLog.annotation.ActionLog;
import com.linln.component.shiro.ShiroUtil;
import com.linln.modules.system.domain.Role;
import com.linln.modules.system.domain.User;
import com.linln.modules.system.service.RoleService;
import com.linln.modules.system.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 小懒虫
 * @date 2018/8/14
 */
@Controller
public class LoginController implements ErrorController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private ProcessTaskDeviceRepository processTaskDeviceRepository;

    @Autowired
    private UserDeviceHistoryRepository userDeviceHistoryRepository;

    /**
     * 跳转到登录页面
     */
    @GetMapping("/login")
    public String toLogin(Model model) {
        ProjectProperties properties = SpringContextUtil.getBean(ProjectProperties.class);
        model.addAttribute("isCaptcha", properties.isCaptchaOpen());
        return "/login";
    }

    /**
     * 实现登录
     */
    @PostMapping("/login")
    @ResponseBody
    @ActionLog(key = UserAction.USER_LOGIN, action = UserAction.class)
    public ResultVo login(String username, String password, String captcha, String rememberMe) {
        // 判断账号密码是否为空
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {

            throw new ResultException(ResultEnum.USER_NAME_PWD_NULL);
        }

        // 判断验证码是否正确
        ProjectProperties properties = SpringContextUtil.getBean(ProjectProperties.class);
        if (properties.isCaptchaOpen()) {
            Session session = SecurityUtils.getSubject().getSession();
            String sessionCaptcha = (String) session.getAttribute("captcha");
            if (StringUtils.isEmpty(captcha) || StringUtils.isEmpty(sessionCaptcha)
                    || !captcha.toUpperCase().equals(sessionCaptcha.toUpperCase())) {
                throw new ResultException(ResultEnum.USER_CAPTCHA_ERROR);
            }
            session.removeAttribute("captcha");
        }

        // 1.获取Subject主体对象
        Subject subject = SecurityUtils.getSubject();

        // 2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        // 3.执行登录，进入自定义Realm类中
        try {
            // 判断是否自动登录
            if (rememberMe != null) {
                token.setRememberMe(true);
            } else {
                token.setRememberMe(false);
            }
            subject.login(token);

            // 判断是否拥有后台岗位
            User user = ShiroUtil.getSubject();
            if (roleService.existsUserOk(user.getId())) {
                return ResultVoUtil.success("登录成功", new URL("/"));
            } else {
                SecurityUtils.getSubject().logout();
                return ResultVoUtil.error("您不是后台管理员！");
            }
        } catch (LockedAccountException e) {
            return ResultVoUtil.error("该账号已被冻结");
        } catch (AuthenticationException e) {
            return ResultVoUtil.error("用户名或密码错误");
        }
    }

    @PostMapping("/cardCrashLogin")
    @ResponseBody
    public ResultVo cardCrashLogin(@RequestBody CardLoginReq req){
        if(req.getCardSequence()==null||"".equals(req.getCardSequence())){
            return ResultVoUtil.error("参数错误：请输入工号");
        }
        if(req.getDeviceCode()==null||"".equals(req.getDeviceCode())){
            return ResultVoUtil.error("参数错误：检测不到设备");
        }
        User user = userService.findUserByCardNo(req.getCardSequence());
        if(user==null){
            //该工号不存在
            return ResultVoUtil.error("该工号不存在");
        }
        Set<Role>  roleSet = roleService.getUserOkRoleList(user.getId());
        String roleNames = "";
        for(Role role : roleSet){
            roleNames = roleNames + role.getTitle() + "|";
        }
        user.setRoleNames(roleNames);

        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername()+"|","password");
        currentUser.login(token);
        //记录上机人员
//        ProcessTaskDevice ptd = processTaskDeviceRepository.findByPTCodeDeviceCode(req.getDeviceCode(),req.getProcessTaskCode() );
//        if(ptd!=null){
//            ptd.setUser_ids(user.getNickname());
//            processTaskDeviceRepository.save(ptd);
//        }

        UserDeviceHistory old = userDeviceHistoryRepository.findOnlyUpTimeRecord(req.getDeviceCode());

        if(old==null){
            UserDeviceHistory history = new UserDeviceHistory();
            history.setDevice_code(req.getDeviceCode());
            history.setUser_id(user.getId());
            history.setUser_name(user.getNickname());
            history.setProcess_task_code(req.getProcessTaskCode());
            history.setUp_time(new Date());
            history.setDo_type("");
            userDeviceHistoryRepository.save(history);
        }else {
           return ResultVoUtil.error("请先下机！");

        }

        return ResultVoUtil.success("上机成功",user);

    }

    //下机操作
    @PostMapping("/logoutDevice")
    @ResponseBody
    public ResultVo logoutDevice(@RequestBody CardLoginReq req){
        User user = userService.findUserByCardNo(req.getCardSequence());
        if(user==null){
            //该工号不存在
            return ResultVoUtil.error("该工号不存在");
        }
        UserDeviceHistory history = userDeviceHistoryRepository.findOnlyUpTimeRecord(req.getDeviceCode());

        if(!user.getId().equals(history.getUser_id())){
            return ResultVoUtil.error("上下机员工不一致!");
        }
        history.setDown_time(new Date());
        userDeviceHistoryRepository.save(history);
        SecurityUtils.getSubject().logout();

        return ResultVoUtil.success("下机成功");

    }



    /**
     * 验证码图片
     */
    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //设置响应头信息，通知浏览器不要缓存
        response.setHeader("Expires", "-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "-1");
        response.setContentType("image/jpeg");

        // 获取验证码
        String code = CaptchaUtil.getRandomCode();
        // 将验证码输入到session中，用来验证
        request.getSession().setAttribute("captcha", code);
        // 输出到web页面
        ImageIO.write(CaptchaUtil.genCaptcha(code), "jpg", response.getOutputStream());
    }

    /**
     * 退出登录
     */
    @GetMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:/login";
    }

    /**
     * 权限不足页面
     */
    @GetMapping("/noAuth")
    public String noAuth() {
        return "/system/main/noAuth";
    }

    /**
     * 自定义错误页面
     */
    @Override
    public String getErrorPath() {
        return "/error";
    }

    /**
     * 处理错误页面
     */
    @RequestMapping("/error")
    public String handleError(Model model, HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String errorMsg = "好像出错了呢！";
        if (statusCode == 404) {
            errorMsg = "页面找不到了！好像是去火星了~";
        }

        model.addAttribute("statusCode", statusCode);
        model.addAttribute("msg", errorMsg);
        return "/system/main/error";
    }
//    公共详情页面
    @GetMapping("/detail")
    public String detail() {
        return "/common/detail";
    }
    //    公共基础数据选择页面
    @GetMapping("/imTable")
    public String choseTable() {
        return "/common/imtable";
    }

    //    公共基础数据选择页面
    @GetMapping("/userTable")
    public String userTable() {
        return "/common/userTable";
    }

    //   工序选择
    @GetMapping("/gonxvTable")
    public String gonxvTable() {
        return "/common/gonxvTable";
    }
}
