package top.werls.tools.system.service.impl;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import top.werls.tools.common.utils.JwtTokenUtils;
import top.werls.tools.system.param.LoginParam;
import top.werls.tools.system.service.SysUserService;
import top.werls.tools.system.vo.LoginVo;



@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private UserDetailsServiceImpl userDetailsService;

    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private JwtTokenUtils tokenUtils;

    /**
     * 登录
     *
     * @param param
     * @return
     */
    @Override
    public LoginVo login(LoginParam param) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(param.getUsername());

        if (!passwordEncoder.matches(param.getPassword(), userDetails.getPassword())) {
            throw new BadCredentialsException("密码错误");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(tokenUtils.generateToken(userDetails.getUsername()));
        return loginVo;
    }

}
