package com.example.entity;

public class Admin {
    private Integer id;

    private String loginAcount;

    private String userPassword;

    private String userName;

    private String email;

    private String createTime;

    public Integer getId() {
        return id;
    }

    public Admin() {
    }

    public Admin(Integer id, String loginAcount, String userPassword, String userName, String email, String createTime) {
        this.id = id;
        this.loginAcount = loginAcount;
        this.userPassword = userPassword;
        this.userName = userName;
        this.email = email;
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", loginAcount='" + loginAcount + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginAcount() {
        return loginAcount;
    }

    public void setLoginAcount(String loginAcount) {
        this.loginAcount = loginAcount == null ? null : loginAcount.trim();
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }
}