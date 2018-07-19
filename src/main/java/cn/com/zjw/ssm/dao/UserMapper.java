package cn.com.zjw.ssm.dao;

import cn.com.zjw.ssm.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    public User getUser(@Param("loginName") String loginName);
}
