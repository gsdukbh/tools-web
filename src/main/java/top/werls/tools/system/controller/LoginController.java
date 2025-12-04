package top.werls.tools.system.controller;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.werls.tools.common.ResultData;
import top.werls.tools.common.annotation.RequestLimit;
import top.werls.tools.common.annotation.RequestRateLimit;
import top.werls.tools.system.param.LoginParam;
import top.werls.tools.system.service.SysUserService;
import top.werls.tools.system.vo.LoginVo;




@Slf4j
@RestController
public class LoginController {


    @Resource
    private SysUserService userService;

    @PostMapping("/login")
    @RequestRateLimit(frequency = 5)
    public ResultData<LoginVo> login(@RequestBody LoginParam param) {
        return ResultData.success(userService.login(param));
    }
}
