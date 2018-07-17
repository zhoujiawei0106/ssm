package cn.com.zjw.ssm.controller.common;

import cn.com.zjw.ssm.controller.BaseController;
import cn.com.zjw.ssm.enetity.SystemParam;
import cn.com.zjw.ssm.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 通用
 */
@Controller
public class CommonController extends BaseController {

    @Autowired
    private CommonService commonService;

    /**
     * 获取系统支持的语言种类
     * @return
     */
    @RequestMapping("/languages")
    @ResponseBody
    public List<SystemParam> languages() {
        List<SystemParam> list = commonService.languages();
        return list;
    }
}
