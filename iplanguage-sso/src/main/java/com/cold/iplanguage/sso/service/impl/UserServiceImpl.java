package com.cold.iplanguage.sso.service.impl;

import com.cold.iplanguage.common.api.CommonResult;
import com.cold.iplanguage.mapper.TbUserMapper;
import com.cold.iplanguage.model.TbUser;
import com.cold.iplanguage.model.TbUserExample;
import com.cold.iplanguage.sso.domain.MemberDetails;
import com.cold.iplanguage.sso.service.UserService;
import com.cold.security.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Auther: ohj
 * @Date: 2019/11/15 16:19
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private TbUserMapper userMapper;
    @Override
    public TbUser getUserByUsername(String username) {
        TbUserExample example = new TbUserExample();
//        TbUserExample.Criteria criteria1 = example.createCriteria();
//        criteria1.andUsernameEqualTo(username);
//        TbUserExample.Criteria criteria2 = example.createCriteria();
//        criteria2.andPhoneEqualTo(username);
//        TbUserExample.Criteria criteria3 = example.createCriteria();
//        criteria3.andEmailEqualTo(username);
//        example.or(criteria1);
        example.or().andUsernameEqualTo(username);
        example.or().andPhoneEqualTo(username);
        example.or().andEmailEqualTo(username);
        List<TbUser> users = userMapper.selectByExample(example);
        if(users!=null&&users.size()>0){
            return users.get(0);
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        //获取用户信息
        TbUser user = getUserByUsername(username);
        if (user != null) {
            return new MemberDetails(user);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if(!passwordEncoder.matches(password,userDetails.getPassword())){
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    @Override
    public CommonResult updatePassword(String telephone, String password) {
        TbUserExample example = new TbUserExample();
        example.createCriteria().andPhoneEqualTo(telephone);
        List<TbUser> memberList = userMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(memberList)){
            return CommonResult.failed("该账号不存在");
        }
//        //验证验证码
//        if(!verifyAuthCode(authCode,telephone)){
//            return CommonResult.failed("验证码错误");
//        }
        TbUser tbUser = memberList.get(0);
        tbUser.setUserpass(passwordEncoder.encode(password));
        userMapper.updateByPrimaryKeySelective(tbUser);
        return CommonResult.success(null,"密码修改成功");
    }

    @Override
    public TbUser getCurrentMember() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        MemberDetails memberDetails = (MemberDetails) auth.getPrincipal();
        return memberDetails.getTbUser();
    }

    @Override
    public String refreshToken(String oldToken) {
        return jwtTokenUtil.refreshHeadToken(oldToken);
    }
}