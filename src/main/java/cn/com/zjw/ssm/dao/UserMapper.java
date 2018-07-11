package cn.com.zjw.ssm.dao;

import cn.com.zjw.ssm.dto.UserInfo;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    public UserInfo getUser(@Param("loginName") String loginName);
}
