package top.werls.tools.system.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.werls.tools.common.ResultData;
import top.werls.tools.system.param.LoginParam;
import top.werls.tools.system.service.SysUserService;
import top.werls.tools.system.vo.LoginVo;

import javax.annotation.Resource;


@Slf4j
@RestController
public class LoginController {


    @Resource
    private SysUserService userService;

    @PostMapping("/login")
    public ResultData<LoginVo> login(@RequestBody LoginParam param) {
        return ResultData.success(userService.login(param));
    }
}
