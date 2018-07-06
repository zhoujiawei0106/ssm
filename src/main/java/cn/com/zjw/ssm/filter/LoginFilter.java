package cn.com.zjw.ssm.filter;


import cn.com.zjw.ssm.dto.UserInfo;
import cn.com.zjw.ssm.service.UserService;
import cn.com.zjw.ssm.utils.SpringContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        String loginName = request.getParameter("loginName");
        String password = request.getParameter("password");
        String code = request.getParameter("code");

        String sessionCode = request.getSession().getAttribute("code").toString();

        UserInfo userInfo = SpringContextUtils.getBean(UserService.class).getUserInfo(loginName);

//        UUID uuid=UUID.randomUUID();
//        String str = uuid.toString();
//        String uuidStr=str.replace("-", "");
    }

    @Override
    public void destroy() {

    }
}
