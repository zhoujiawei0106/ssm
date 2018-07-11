package cn.com.zjw.ssm.service.impl;

import cn.com.zjw.ssm.dao.UserMapper;
import cn.com.zjw.ssm.dto.UserInfo;
import cn.com.zjw.ssm.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

@Service
@Transactional(rollbackFor = Exception.class)
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void loginCheck(HttpServletRequest request, Model model) {
        // 用户名
        String loginName = request.getParameter("loginName");
        // 密码
        String password = request.getParameter("password");
        // 输入的验证码
        String code = request.getParameter("code");

        // 系统生成的验证码
        String sessionCode = request.getSession().getAttribute("code").toString();

        // 校验验证码
        if (!sessionCode.equals(code)) {
            model.addAttribute("msg", "验证码不正确，请重新输入");
            return;
        }

        // 获取登陆的用户信息
        UserInfo userInfo = userMapper.getUser(loginName);
        if (userInfo == null) {
            model.addAttribute("mag", "用户名或密码不正确");
            return;
        } else if (!userInfo.getLoginName().equals(loginName) || !userInfo.getPassword().equals(password)) {
            model.addAttribute("mag", "用户名或密码不正确");
            return;
        }
    }
}
