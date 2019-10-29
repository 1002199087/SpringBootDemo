package com.demo.webdemo.entity.response;

import com.demo.webdemo.entity.db.User;

public class FindUserResponse {
    private String code;
    private String msg;
    private User user;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
