package cn.com.zjw.ssm.utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanaugeUtils {

    public static void main(String[] args) {
//        Locale defaultLocale=Locale.getDefault();
        Locale defaultLocale = new Locale("zh", "");
        System.out.println("country="+defaultLocale.getCountry());
        System.out.println("language="+defaultLocale.getLanguage());
        ResourceBundle rb = ResourceBundle.getBundle("message/messages", defaultLocale);
        System.out.println(rb.getString("msg.code.overdue"));
    }
}
