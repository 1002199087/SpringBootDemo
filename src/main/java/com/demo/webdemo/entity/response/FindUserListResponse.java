package com.demo.webdemo.entity.response;

import com.demo.webdemo.entity.db.User;

import java.util.List;

public class FindUserListResponse {
    private String code;
    private String msg;
    private List<User> users;

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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
