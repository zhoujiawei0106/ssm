package cn.com.zjw.ssm.controller;

import cn.com.zjw.ssm.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        loginService.loginCheck(request, model);
        return "static/login.jsp";
    }
}
