package cn.com.zjw.ssm.filter;


import cn.com.zjw.ssm.dto.UserInfo;
import cn.com.zjw.ssm.listener.SingleLoginListener;
import cn.com.zjw.ssm.service.UserService;
import cn.com.zjw.ssm.utils.SpringContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        HttpSession session = request.getSession();

        // 判断用户是否登陆了
        boolean isLogin = SingleLoginListener.isOnline(session);
        // 登陆了，直接跳转到主页;
        if (isLogin) {
            request.getRequestDispatcher("/view/static/index.html").forward(request, response);
        } else {
            // 获取请求的url
            String url = request.getRequestURI();
            // 如果url不是login，说明还没有登陆，强制跳转到登陆页面
            if (url.equals("/login")) {
                request.getRequestDispatcher("/view/static/login.jsp").forward(request, response);
            } else {
                String loginName = request.getParameter("loginName");
                SingleLoginListener.isLogin(session, loginName);
                request.getRequestDispatcher("/view/static/index.html").forward(request, response);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
