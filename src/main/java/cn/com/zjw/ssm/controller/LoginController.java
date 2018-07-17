package cn.com.zjw.ssm.controller;

import cn.com.zjw.ssm.enetity.SystemParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登陆
 */
@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login() throws Exception {
        return "static/index";
    }
}
