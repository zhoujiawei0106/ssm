package cn.com.zjw.ssm.filter;


import cn.com.zjw.ssm.constants.CodeConstants;
import cn.com.zjw.ssm.entity.User;
import cn.com.zjw.ssm.listener.SingleLoginListener;
import cn.com.zjw.ssm.service.UserService;
import cn.com.zjw.ssm.utils.JsonParseUtils;
import cn.com.zjw.ssm.utils.SpringContextUtils;
import org.codehaus.plexus.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class LoginFilter implements Filter {

    private String[] excludeUrls = new String[]{};

    // 验证码失效时间(分)
    private static final int OVERDUE_TIME = 1;

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

        // 当前访问的路径
        String url = request.getRequestURI();
        System.err.println("===============================" + url);

        for (String paramUrl : excludeUrls) {
            // 如果直接访问登陆页面，或访问配置文件中配置的不过滤url不做处理
            if (url.endsWith(paramUrl)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }


        boolean flag = false;
        // 用户名
        String loginName = request.getParameter("loginName");
        // 如果是登陆的链接，直接校验用户
        if (url.equals("/login")) {
            // 密码
            String password = request.getParameter("password");
            // 输入的验证码
            String code = request.getParameter("code");

            // 系统生成的验证码
            Map<String, Object> codeMap = (Map)request.getSession().getAttribute("codeMap");
            String sessionCode = codeMap.get("code").toString();
            Calendar calendar = (Calendar) codeMap.get("time");
            calendar.add(Calendar.MINUTE, OVERDUE_TIME);
            Calendar nowTime = Calendar.getInstance();

            response.setContentType("text/html;charset=UTF-8");

            if (calendar.compareTo(nowTime) < 0) {
                Map<String, Object> map = this.failMap("验证码已过期,请重新输入");
                response.getWriter().write(JsonParseUtils.toJson(map));
                return;
            }

            // 校验验证码
            if (!sessionCode.equals(code)) {
                Map<String, Object> map = this.failMap("验证码不正确,请重新输入");
                response.getWriter().write(JsonParseUtils.toJson(map));
                return;
            }

            // 获取登陆的用户信息
            User user = SpringContextUtils.getBean(UserService.class).getUser(loginName);
            if (user == null) {
                Map<String, Object> map = this.failMap("用户名或密码不正确,请重新输入");
                response.getWriter().write(JsonParseUtils.toJson(map));
                return;
            } else if (!user.getLoginName().equals(loginName) || !user.getPassword().equals(password)) {
                Map<String, Object> map = this.failMap("用户名或密码不正确,请重新输入");
                response.getWriter().write(JsonParseUtils.toJson(map));
                return;
            }

            // 将获得的用户信息存放在session中
            session.setAttribute(CodeConstants.SESSION_LOGIN_USER, user);
            flag = true;
        }

        // 其他链接访问判断用户是否登陆了
        boolean isLogin = SingleLoginListener.isOnline(session, loginName);
        // 登陆了，直接跳转到主页;
        if (isLogin) {
            request.getRequestDispatcher("/WEB-INF/views/static/index.html").forward(request, response);
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        } else {
            // 为true时，登陆验证通过
            if (!flag) {
                request.getRequestDispatcher("/WEB-INF/views/static/login.html").forward(request, response);
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
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
        map.put("msg", msg);
        map.put("flag", false);
        return map;
    }
}
