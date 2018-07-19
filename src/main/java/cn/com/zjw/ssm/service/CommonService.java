package cn.com.zjw.ssm.service;

import cn.com.zjw.ssm.entity.SystemParam;

import java.util.List;

public interface CommonService {

    /**
     * 获取系统支持语言
     * @return
     */
    public List<SystemParam> languages();
}
