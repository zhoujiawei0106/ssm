package cn.com.zjw.ssm.service.impl;

import cn.com.zjw.ssm.constants.CodeConstants;
import cn.com.zjw.ssm.dao.CommonMapper;
import cn.com.zjw.ssm.entity.SystemParam;
import cn.com.zjw.ssm.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CommonServiceImpl implements CommonService {

    @Autowired
    private CommonMapper commonMapper;

    @Override
    public List<SystemParam> languages() {
        return commonMapper.languages(CodeConstants.CODE_TYPE_LANGUAGE);
    }
}
