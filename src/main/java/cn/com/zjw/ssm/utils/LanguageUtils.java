package cn.com.zjw.ssm.utils;

import cn.com.zjw.ssm.constants.CodeConstants;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageUtils {

    // 默认初始化为当前系统的语言
    private static Locale defaultLocale = new Locale(Locale.getDefault().getLanguage(), "");

    /**
     * 获取指定的消息内容
     * @param id
     * @return
     */
    public static String getMessage(String id) {
        ResourceBundle rb = ResourceBundle.getBundle("message/messages", defaultLocale);
        return rb.getString(id);
    }

    public static void setLanguage(int code) {
        if (code == CodeConstants.LANGUAGE_CN_CODE) {
            defaultLocale = new Locale(CodeConstants.LANGUAGE_CN_TYPE, "");
        } else if (code == CodeConstants.LANGUAGE_EN_CODE) {
            defaultLocale = new Locale(CodeConstants.LANGUAGE_EN_TYPE, "");
        }
    }

    public static void main(String[] args) {
        Locale defaultLocale = new Locale("zh", "");
        ResourceBundle rb = ResourceBundle.getBundle("message/messages", defaultLocale);
        System.out.println();
//        System.out.println(rb.getString("msg.code.overdue"));
    }
}
