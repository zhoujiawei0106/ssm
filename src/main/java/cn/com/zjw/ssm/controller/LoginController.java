package cn.com.zjw.ssm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 登陆
 */
@Controller
public class LoginController extends BaseController {

    @RequestMapping("/login")
    @ResponseBody
    public Map<String, Object> login() throws Exception {
        return success("/index");
    }

    @RequestMapping("/index")
    public String index() throws Exception {
        return "static/index";
    }
}
