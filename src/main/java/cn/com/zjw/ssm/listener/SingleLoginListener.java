package cn.com.zjw.ssm.listener;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SingleLoginListener implements HttpSessionListener {

    private static Map<String, String> userMap = new HashMap<String, String>();

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
     * @param session
     * @param loginName 用户名
     * @return boolean 该用户是否在线的标志
     */
    public static boolean isOnline(HttpSession session, String loginName) {
        // 如果userMap中存有sessionId并且对应的value和loginName相同
        if (StringUtils.isNotBlank(userMap.get(session.getId())) && userMap.get(session.getId()).equals(loginName)) {
            return true;
        }
        return false;
    }

    /**
     * 判断用户是否登陆
     * @param session
     * @param loginName 用户名
     * @return boolean 该用户是否登陆的标志
     */
    public static boolean isLogin(HttpSession session, String loginName) {
        if (userMap.containsKey(session.getId())) {
            if (userMap.get(session.getId()) == "" || userMap.get(session.getId()).equals(loginName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 登陆后将数据放到map中
     * @param session
     * @param loginName
     * @return
     */
    public static void login(HttpSession session, String loginName) {
        // 如果该用户已经登录过
        if (userMap.containsValue(loginName)) {
            // 将所有userMap中的loginName滞空
            for(String key : userMap.keySet()) {
                String val = userMap.get(key);
                if (((String)val).equals(loginName)) {
                    userMap.put(key, "");
                }
            }
            // 添加新的的sessionId和loginName
            userMap.put(session.getId(), loginName);
        } else {
            // 如果该用户没登录过，直接添加新的sessionId和loginName
            userMap.put(session.getId(), loginName);
        }
    }
}
