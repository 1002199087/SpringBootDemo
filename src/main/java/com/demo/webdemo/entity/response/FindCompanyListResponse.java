package com.demo.webdemo.entity.response;

import com.demo.webdemo.entity.CompanyDao;
import com.demo.webdemo.entity.db.Company;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class FindCompanyListResponse {
    private String code;
    private String msg;
    private List<CompanyDao> companies;

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

    public List<CompanyDao> getCompanies() {
        return companies;
    }

    public void setCompanies(List<CompanyDao> companies) {
        this.companies = companies;
    }
}
