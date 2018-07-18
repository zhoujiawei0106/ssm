package cn.com.zjw.ssm.filter;

import javax.imageio.ImageIO;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 登陆页面验证码filter
 */
public class AuthCodeFilter implements Filter {

    public static void main(String[] args) {
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            int num = random.nextInt(6);
            System.out.println(num);
        }
    }

    @Override
    public void init(FilterConfig filterConfig){

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse)response;
        HttpServletRequest req = (HttpServletRequest)request;

        // ☆1☆--相比纯java方式有变化的地方
        // 设置http响应头---告诉浏览器我现在发的是这个图片格式的数据，你用相应的方式来解析
        res.setContentType("image/jpeg");

        // 定义图片的宽和高
        int w = 60;
        int h = 21;

        // 声明一个RGB格式的内存中的图片
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();

        // 把背景变白色
        g.setColor(Color.white);
        g.fillRect(0, 0, w, h);
        // 设置字体
        g.setFont(new Font("aa", Font.BOLD, 18));

        // 产生并draw出4个随机数字
        Random r = new Random();
        String code = "";
        for (int i = 0; i < 4; i++) {
            // 生成0~9之间的随机整数
            int a = r.nextInt(10);
            // 产生随机的垂直位置
            int y = 15 + r.nextInt(6);
//            int y = 18;
            // 产生随机颜色
            Color c = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
            g.setColor(c);

            g.drawString("" + a, i * 15, y);

            code += a;
        }

        HttpSession session = req.getSession();
        session.setAttribute("codeMap", codeMap(code));

        // 画几条干扰线
//        for (int i = 0; i < 10; i++) {
//            // 产生随机颜色
//            Color c = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
//            g.setColor(c);
//            g.drawLine(r.nextInt(60), r.nextInt(30), r.nextInt(60),
//                    r.nextInt(30));
//        }

        g.dispose();// 类似于IO中的flush(),把图形数据刷到img中
        // 把内存图片img对象保存到一个jpg文件
        ImageIO.write(img, "JPEG", res.getOutputStream());// ☆2☆
    }

    @Override
    public void destroy() {

    }

    private static final Map<String, Object> codeMap(String code) {
        Map<String, Object> map = new HashMap<String, Object>();
        Calendar calendar = Calendar.getInstance();
        map.put("code", code);
        map.put("time", calendar);
        return map;
    }
}
