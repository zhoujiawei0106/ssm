package cn.com.zjw.ssm.controller;

import cn.com.zjw.ssm.constants.CodeConstants;
import cn.com.zjw.ssm.enetity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class BaseController {

    /**
     * 获取session中的用户对象
     * @param request
     * @return
     */
    protected User getSessionUser(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        return (User) session.getAttribute(CodeConstants.SESSION_LOGIN_USER);
    }
}
