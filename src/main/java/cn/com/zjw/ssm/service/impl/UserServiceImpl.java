package cn.com.zjw.ssm.service.impl;

import cn.com.zjw.ssm.dao.UserMapper;
import cn.com.zjw.ssm.entity.User;
import cn.com.zjw.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUser(String loginName) {
        return userMapper.getUser(loginName);
    }
}
