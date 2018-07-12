package cn.com.zjw.ssm.filter;


import cn.com.zjw.ssm.dto.UserInfo;
import cn.com.zjw.ssm.listener.SingleLoginListener;
import cn.com.zjw.ssm.service.UserService;
import cn.com.zjw.ssm.utils.SpringContextUtils;
import org.codehaus.plexus.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

public class LoginFilter implements Filter {

    private String[] excludeUrls = new String[]{};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String urls = filterConfig.getInitParameter("excludeUrls");
        if (StringUtils.isNotBlank(urls)) {
            excludeUrls = urls.split(";");
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        HttpSession session = request.getSession();

        String url = request.getRequestURI();
        for (String paramUrl : excludeUrls) {
            // 如果直接访问登陆页面，或访问配置文件中配置的不过滤url不做处理
            if (paramUrl.equals("login.jsp")) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else if (url.indexOf(paramUrl) >= 0) {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }

        // 其他链接访问判断用户是否登陆了
        boolean isLogin = SingleLoginListener.isOnline(session);
        // 登陆了，直接跳转到主页;
        if (isLogin) {
            request.getRequestDispatcher("index.html").forward(request, response);
        } else {
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
     }

    @Override
    public void destroy() {

    }
}
