package cn.com.zjw.ssm.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SingleLoginListener implements HttpSessionListener {

    private static Map userMap = new HashMap();

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        synchronized(this){
            userMap.remove(httpSessionEvent.getSession().getId());
        }
    }

    /**
     * 判断用户是否在线
     *
     * @param session 登录的用户名称
     * @return boolean 该用户是否在线的标志
     */
    public static boolean isOnline(HttpSession session, String loginName) {
        boolean flag;
        if (userMap.containsValue(session.getId())) {
            flag = true;
            isLogin(session, loginName);
        } else {
            flag = false;
        }
        return flag;
    }

    /**
     * 登陆后将数据放到map中
     * @param session
     * @param loginName
     * @return
     */
    private static void isLogin(HttpSession session, String loginName) {
        // 如果该用户已经登录过，则使上次登录的用户掉线(依据使用户名是否在userMap中)
        if (userMap.containsValue(loginName)) {
            // 遍历原来的userMap，删除原用户名对应的sessionID(即删除原来的sessionID和username)
            Iterator iter = userMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                Object val = entry.getValue();
                if (((String)val).equals(loginName)) {
                    userMap.remove(key);
                }
            }
            // 添加现在的sessionID和username
            userMap.put(session.getId(), loginName);
        } else { // 如果该用户没登录过，直接添加现在的sessionID和username
            userMap.put(session.getId(), loginName);
        }
    }
}
