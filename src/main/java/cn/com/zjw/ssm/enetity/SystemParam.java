package cn.com.zjw.ssm.enetity;

import java.io.Serializable;

public class SystemParam implements Serializable {

    /**
     * 代码id
     */
    private Integer codeId;

    /**
     * 代码类型
     */
    private String codeType;

    /**
     * 代码值
     */
    private String codeValue;

    /**
     * 代码中文
     */
    private String codeDesc;

    public Integer getCodeId() {
        return codeId;
    }

    public void setCodeId(Integer codeId) {
        this.codeId = codeId;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    public String getCodeDesc() {
        return codeDesc;
    }

    public void setCodeDesc(String codeDesc) {
        this.codeDesc = codeDesc;
    }
}
