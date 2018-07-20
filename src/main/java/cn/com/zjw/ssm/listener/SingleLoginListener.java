package cn.com.zjw.ssm.listener;

import org.codehaus.plexus.util.StringUtils;

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
     *
     * @param session 登录的用户名称
     * @return boolean 该用户是否在线的标志
     */
    public static boolean isOnline(HttpSession session, String loginName) {
        boolean flag = false;

        // 如果不是登陆或者从未登陆过，loginName才会为空
        if (session == null || StringUtils.isBlank(loginName)) {
            return flag;
        }

        if (userMap.containsValue(session.getId())) {
            flag = true;
        } else if (StringUtils.isNotBlank(loginName)) {
            isLogin(session, loginName);
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
            // 遍历原来的userMap，删除原用户名对应的sessionID(即删除原来的sessionID和loginName)
            Iterator iter = userMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                Object val = entry.getValue();
                if (((String)val).equals(loginName) && userMap.containsKey(session.getId())) {
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
