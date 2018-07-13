package cn.com.zjw.ssm.filter;


import cn.com.zjw.ssm.dao.UserMapper;
import cn.com.zjw.ssm.dto.UserInfo;
import cn.com.zjw.ssm.listener.SingleLoginListener;
import cn.com.zjw.ssm.service.UserService;
import cn.com.zjw.ssm.utils.JsonParseUtil;
import cn.com.zjw.ssm.utils.SpringContextUtils;
import org.codehaus.plexus.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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

        // 如果是登陆的链接，直接校验用户
        if (url.equals("/login")) {
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
                Map<String, Object> map = this.failMap("验证码不正确，请重新输入");
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write(JsonParseUtil.toJson(map));
                return;
            }

            // 获取登陆的用户信息
            UserInfo userInfo = SpringContextUtils.getBean(UserMapper.class).getUser(loginName);
            if (userInfo == null) {
                request.setAttribute("mag", "用户名或密码不正确");
                return;
            } else if (!userInfo.getLoginName().equals(loginName) || !userInfo.getPassword().equals(password)) {
                request.setAttribute("mag", "用户名或密码不正确");
                return;
            }
        }

        for (String paramUrl : excludeUrls) {
            // 如果直接访问登陆页面，或访问配置文件中配置的不过滤url不做处理
            if (url.endsWith(paramUrl)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }

        // 其他链接访问判断用户是否登陆了
        boolean isLogin = SingleLoginListener.isOnline(session);
        // 登陆了，直接跳转到主页;
        if (isLogin) {
            request.getRequestDispatcher("index.html").forward(request, response);
            return;
        } else {
            request.getRequestDispatcher("login.html").forward(request, response);
            return;
        }
     }

    @Override
    public void destroy() {

    }

    /**
     * 验证失败的返回值
     * @author zhoujiawei
     * @param msg
     * @return
     */
    private Map<String, Object> failMap(String msg) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("msg", "验证码不正确，请重新输入");
//        map.put("loginName", loginName);
//        map.put("password", password);
        map.put("flag", false);
        return map;
    }
}
