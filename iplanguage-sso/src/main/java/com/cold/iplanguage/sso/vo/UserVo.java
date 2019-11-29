package com.cold.iplanguage.sso.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Auther: ohj
 * @Date: 2019/1/14 08:59
 * @Description:
 */
public class UserVo implements Serializable {

    private Long userId;
    private String username;
    private String phone;
    private String email;
    private long userMeal;
    private String token;
    private String tokenHead;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getUserMeal() {
        return userMeal;
    }

    public void setUserMeal(long userMeal) {
        this.userMeal = userMeal;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenHead() {
        return tokenHead;
    }

    public void setTokenHead(String tokenHead) {
        this.tokenHead = tokenHead;
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", userMeal=" + userMeal +
                ", token='" + token + '\'' +
                ", tokenHead='" + tokenHead + '\'' +
                '}';
    }
}