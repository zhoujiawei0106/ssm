package cn.com.zjw.ssm.dao;

import cn.com.zjw.ssm.entity.SystemParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommonMapper {

    public List<SystemParam> languages(@Param("codeType") String codeType);
}
