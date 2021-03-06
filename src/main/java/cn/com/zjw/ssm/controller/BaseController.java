package cn.com.zjw.ssm.controller;

import cn.com.zjw.ssm.constants.CodeConstants;
import cn.com.zjw.ssm.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

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

    /**
     * 请求成功返回前台数据
     * @param data
     * @return
     */
    public Map<String, Object> success(String data) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("flag", true);
        map.put("data", data);
        return map;
    }

    /**
     * 请求成功返回前台数据
     * @param data
     * @return
     */
    public Map<String, Object> success(String data, String msg) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("flag", true);
        map.put("data", data);
        map.put("msg", msg);
        return map;
    }

    /**
     * 请求失败返回前台数据
     * @param msg
     * @return
     */
    public Map<String, Object> fail(String msg) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("flag", false);
        map.put("msg", msg);
        return map;
    }
}
