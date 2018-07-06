package cn.com.zjw.ssm.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class UserInfo implements Serializable {

    private String id;

    private String loginName;

    private String userName;

    private String password;

    private Integer loginFailTimes;

    private Timestamp LastLoginDate;
}
