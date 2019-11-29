package com.cold.iplanguage.sso.domain;

import com.cold.iplanguage.model.TbUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

/**
 * @Auther: ohj
 * @Date: 2019/11/18 08:17
 * @Description:用户详情封装
 */
public class MemberDetails implements UserDetails {
    private TbUser tbUser;

    public MemberDetails(TbUser tbUser) {
        this.tbUser = tbUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的权限
        return Arrays.asList(new SimpleGrantedAuthority("TEST"));
    }

    @Override
    public String getPassword() {
        return tbUser.getUserpass();
    }

    @Override
    public String getUsername() {
        return tbUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return tbUser.getStatus()==1;
    }

    public TbUser getTbUser() {
        return tbUser;
    }
}