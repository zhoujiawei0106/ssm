package cn.com.zjw.ssm.service.impl;

import cn.com.zjw.ssm.dao.UserMapper;
import cn.com.zjw.ssm.dto.UserInfo;
import cn.com.zjw.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserInfo getUserInfo(String loginName) {
        return userMapper.getUser(loginName);
    }
}
