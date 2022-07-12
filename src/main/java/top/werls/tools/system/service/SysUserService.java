package top.werls.tools.system.service;


import top.werls.tools.system.param.LoginParam;
import top.werls.tools.system.vo.LoginVo;

public interface SysUserService {


    /**
     *
     * 登录
     * @param param
     * @return
     */
    LoginVo login(LoginParam param);

}
