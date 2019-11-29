package com.cold.security.config;

import com.cold.iplanguage.common.util.MD5Util;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Auther: ohj
 * @Date: 2019/11/18 08:39
 * @Description: 密码策略
 */
public class MyPasswordEncoder implements PasswordEncoder {

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(MD5Util.MD5((String)rawPassword));
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return MD5Util.MD5((String)rawPassword);
    }
}