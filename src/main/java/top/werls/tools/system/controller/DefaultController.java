package top.werls.tools.system.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.werls.tools.common.utils.IPUtils;



/**
 * @author Jiawei Lee
 * @version TODO
 * @date created 2022/7/12
 * @since on
 */
@Controller
public class DefaultController {

    @RequestMapping("/")
    public  String index(){
        return "rsa";
    }
    @RequestMapping("/public/rsa")
    public  String rsa(){
        return  "rsa";
    }
    @RequestMapping("/public/ip")
    @ResponseBody
    public String getIp(HttpServletRequest request){
        return IPUtils.getIpAddress(request);
    }
}
