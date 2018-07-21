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
     *
     * @param session
     * @return boolean 该用户是否在线的标志
     */
    public static boolean isOnline(HttpSession session, String loginName) {
        // 如果userMap中存有loginName，需要进行下一步判断
        if (userMap.containsValue(loginName)) {
            // 如果当前的sessionId不存在于userMap，或当前的sessionId在userMap中的value和loginName不匹配
            if (!userMap.containsKey(session.getId()) || !(userMap.get(session.getId()).equals(loginName))) {
                return true;
            }
        }
//        boolean flag = false;

//        if (userMap.containsKey(session.getId()) && userMap.get(session.getId()).equals(loginName)) {
//            flag = true;
//        } else if (userMap.containsKey(session.getId()) && !userMap.get(session.getId()).equals(loginName)) {
//            flag = false;
//        }

//        if (userMap.containsValue(session.getId())) {
//            flag = true;
//            isLogin(session, loginName);
//        } else if (StringUtils.isNotBlank(loginName)) {
//            flag = false;
//        }
        return false;
    }

    /**
     * 登陆后将数据放到map中
     * @param session
     * @param loginName
     * @return
     */
    public static void login(HttpSession session, String loginName) {
        // 如果该用户已经登录过，则使上次登录的用户掉线(依据使用户名是否在userMap中)
        if (userMap.containsValue(loginName)) {
            // 遍历原来的userMap，删除原用户名对应的sessionID(即删除原来的sessionID和loginName)
            for(String key : userMap.keySet()) {
                String val = userMap.get(key);
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
