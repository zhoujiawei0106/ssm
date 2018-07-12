package cn.com.zjw.ssm.service;

import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {

    /**
     * 登陆检查
     * @param request
     * @param model
     */
    public void loginCheck(HttpServletRequest request, Model model) throws Exception;
}
