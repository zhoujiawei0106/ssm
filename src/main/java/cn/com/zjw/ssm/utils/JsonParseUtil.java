package cn.com.zjw.ssm.utils;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 对象转json方法
 * @author zhoujiawei
 * @date 2017年11月15日
 */
public class JsonParseUtil {

    private static ObjectMapper mapper;

    public static String toJson(Object bean) throws JsonProcessingException {
        initJackson();

        if (bean == null) {
            return null;
        }

        return mapper.writeValueAsString(bean);
    }

    private static void initJackson() {
        if (mapper == null) {
            mapper = new ObjectMapper();
            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
            mapper.setTimeZone(TimeZone.getDefault());
        }
    }
}
