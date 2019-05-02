package org.fran.microservice.cas.authserver.dao.po;

import java.util.Date;
import java.util.List;

public class AuthUser {
    private Long id;

    private String name;

    private String account;

    private String foreignName;

    private String email;

    private String phone;

    private String password;

    private Integer status;

    private Date createTime;

    List<AuthPermission> authPermissionList;

    public List<AuthPermission> getAuthPermissionList() {
        return authPermissionList;
    }

    public void setAuthPermissionList(List<AuthPermission> authPermissionList) {
        this.authPermissionList = authPermissionList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getForeignName() {
        return foreignName;
    }

    public void setForeignName(String foreignName) {
        this.foreignName = foreignName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}