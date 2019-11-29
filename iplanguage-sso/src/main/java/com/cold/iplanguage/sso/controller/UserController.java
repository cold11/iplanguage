package com.cold.iplanguage.sso.controller;

import cn.hutool.core.bean.BeanUtil;
import com.cold.iplanguage.common.api.CommonResult;
import com.cold.iplanguage.model.TbUser;
import com.cold.iplanguage.sso.service.UserService;
import com.cold.iplanguage.sso.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: ohj
 * @Date: 2019/11/15 14:57
 * @Description: 用户登录模块
 */
@Api(tags = "用户", description = "会员登录注册管理")
@Controller
@RequestMapping("/sso/user")
public class UserController {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private UserService userService;
    @ApiOperation(value = "登录以后返回token")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login(@RequestParam String username,
                              @RequestParam String password) {
        String token = userService.login(username, password);
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        TbUser tbUser = userService.getCurrentMember();
        UserVo userVo = new UserVo();
        BeanUtil.copyProperties(tbUser,userVo);
        userVo.setTokenHead(tokenHead);
        userVo.setUserId(tbUser.getUserid());
        userVo.setToken(token);
//        Map<String, String> tokenMap = new HashMap<>();
//        tokenMap.put("token", token);
//        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(userVo);
    }
    @ApiOperation(value = "刷新token")
    @RequestMapping(value = "/token/refresh", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult refreshToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String refreshToken = userService.refreshToken(token);
        if (refreshToken == null) {
            return CommonResult.failed();
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", refreshToken);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation(value = "获取当前登录用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getUserInfo(Principal principal) {
        String username = principal.getName();
        TbUser tbUser = userService.getUserByUsername(username);
        Map<String, Object> data = new HashMap<>();
        data.put("username", tbUser.getUsername());
        data.put("roles", new String[]{"TEST"});
        data.put("userMeal", tbUser.getUserMeal());
        data.put("searchCount", tbUser.getUserMeal());
        return CommonResult.success(data);
    }
    @ApiOperation(value = "登出功能")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult logout() {
        return CommonResult.success(null);
    }
}