package com.cold.iplanguage.sso.service;

import com.cold.iplanguage.common.api.CommonResult;
import com.cold.iplanguage.model.TbUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther: ohj
 * @Date: 2019/11/15 14:58
 * @Description:
 */
public interface UserService {
    /**
     * 根据用户名获取用户信息
     */
    TbUser getUserByUsername(String username);
    /**
     * 获取用户信息
     */
    UserDetails loadUserByUsername(String username);

    /**
     * 登录后获取token
     */
    String login(String username, String password);

    /**
     * 修改密码
     */
    @Transactional
    CommonResult updatePassword(String telephone, String password);

    /**
     * 获取当前登录会员
     */
    TbUser getCurrentMember();

    String refreshToken(String oldToken);
}